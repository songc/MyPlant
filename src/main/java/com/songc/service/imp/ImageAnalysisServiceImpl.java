package com.songc.service.imp;

import com.songc.core.image.SignalFit;
import com.songc.core.image.TiffImage;
import com.songc.dto.MultiRegionSignalDTO;
import com.songc.dto.SingleRegionSignalDTO;
import com.songc.service.HbaseService;
import com.songc.service.ImageAnalysisService;
import com.songc.util.ExponentFitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.songc.util.ImageUtil.getTranspose;

/**
 * Created By @author songc
 * on 2017/11/7
 */
@Service
public class ImageAnalysisServiceImpl implements ImageAnalysisService {

    private HbaseService hbaseService;

    @Autowired
    public ImageAnalysisServiceImpl(HbaseService hbaseService) {
        this.hbaseService = hbaseService;
    }

    @Override
    public SingleRegionSignalDTO singleRegion(Long datasetId, int startX, int startY, int width, int height) {

        double[] f = hbaseService.findContentByParentId(datasetId).parallelStream()
                .map(TiffImage::new)
                .mapToDouble(image -> image.getRegionGrayAverage(startX, startY, width, height))
                .toArray();
        double[] x = LongStream.rangeClosed(1, f.length).asDoubleStream().toArray();
        double[] f0 = ExponentFitUtil.getOneExpFuncValue(SignalFit.fitOneExponent(x, f), x);
        return new SingleRegionSignalDTO(f, f0);
    }

    @Override
    public MultiRegionSignalDTO multiRegion(Long datasetId, int width, int height) {
        List<double[]> doubles = hbaseService.findContentByParentId(datasetId).parallelStream()
                .map(TiffImage::new)
                .map(image -> image.getAllRegionGrayAverage(width, height))
                .collect(Collectors.toList());
        List<double[]> f = getTranspose(doubles);
        List<double[]> f0 = f.stream().map(s -> {
            double[] x = LongStream.rangeClosed(1, s.length).asDoubleStream().toArray();
            return ExponentFitUtil.getOneExpFuncValue(SignalFit.fitOneExponent(x, s), x);
        }).collect(Collectors.toList());
        return new MultiRegionSignalDTO(f, f0);
    }
}
