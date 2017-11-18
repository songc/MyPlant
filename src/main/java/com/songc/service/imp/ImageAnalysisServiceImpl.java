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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By @author songc
 * on 2017/11/7
 */
@Service
public class ImageAnalysisServiceImpl implements ImageAnalysisService {

    private HbaseService hbaseService;
    private SignalFit signalFit;

    @Autowired
    public ImageAnalysisServiceImpl(HbaseService hbaseService) {
        this.hbaseService = hbaseService;
    }

    @Override
    public SingleRegionSignalDTO singleRegion(Long datasetId, int startX, int startY, int width, int height) {

        double[] f = hbaseService.findByParentId(datasetId).parallelStream()
                .map(TiffImage::new)
                .mapToDouble(image -> image.getRegionGrayAverage(startX, startY, width, height))
                .toArray();
        double[] x = ExponentFitUtil.getX(f.length, 1);
        double[] f0 = ExponentFitUtil.getOneExpFuncValue(SignalFit.fitOneExponent(x, f), x);
        return new SingleRegionSignalDTO(f, f0);
    }

    @Override
    public MultiRegionSignalDTO multiRegion(Long datasetId, int width, int height) {
        List<double[]> doubles = hbaseService.findByParentId(datasetId).parallelStream()
                .map(TiffImage::new)
                .map(image -> image.getAllRegionGrayAverage(width, height))
                .collect(Collectors.toList());
        List<double[]> f = getTranspose(doubles);
        List<double[]> f0 = f.stream().map(s -> {
            double[] x = ExponentFitUtil.getX(s.length, 1);
            return ExponentFitUtil.getOneExpFuncValue(SignalFit.fitOneExponent(x, s), x);
        }).collect(Collectors.toList());
        return new MultiRegionSignalDTO(f, f0);
    }

    /**
     * 对矩阵进行转置
     *
     * @param doubles 需要转置的矩阵
     * @return 转置后的矩阵
     */
    private List<double[]> getTranspose(List<double[]> doubles) {
        int len = doubles.size();
        int lenArray = doubles.get(0).length;
        List<double[]> result = new ArrayList<>();
        for (int i = 0; i < lenArray; i++) {
            double[] array = new double[len];
            int j = 0;
            for (double[] aDouble : doubles) {
                array[j++] = aDouble[i];
            }
            result.add(array);
        }
        return result;
    }
}
