package com.songc.service.imp;

import com.songc.core.image.SignalFit;
import com.songc.core.image.TiffImage;
import com.songc.dao.AnalysisResult;
import com.songc.dto.MultiRegionSignalDTO;
import com.songc.dto.SingleRegionSignalDTO;
import com.songc.service.HbaseService;
import com.songc.service.ImageAnalysisService;
import com.songc.util.ExponentFitUtil;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    private AnalysisResult analysisResult;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ImageAnalysisServiceImpl(HbaseService hbaseService, AnalysisResult analysisResult) {
        this.hbaseService = hbaseService;
        this.analysisResult = analysisResult;
    }

    @Override
    public String singleRegion(Long datasetId, int startX, int startY, int width, int height) {
        String rowKey = StringUtils.reverse(String.format("%016d", datasetId))
                + "-" + startX + "-" + startY + "-" + width + "-" + height;
        String jsonResult = analysisResult.find(rowKey);
        if (jsonResult != null) {
            return jsonResult;
        }
        double[] f = hbaseService.findContentByParentId(datasetId).parallelStream()
                .map(TiffImage::new)
                .mapToDouble(image -> image.getRegionGrayAverage(startX, startY, width, height))
                .toArray();
        double[] x = LongStream.rangeClosed(1, f.length).asDoubleStream().toArray();
        double[] f0 = ExponentFitUtil.getOneExpFuncValue(SignalFit.fitOneExponent(x, f), x);
        SingleRegionSignalDTO signalDTO = new SingleRegionSignalDTO(f, f0);
        try {
            String result = objectMapper.writeValueAsString(signalDTO);
            return analysisResult.save(rowKey, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String multiRegion(Long datasetId, int width, int height) {
        String rowKey = StringUtils.reverse(String.format("%016d", datasetId))
                + "-" + width + "-" + height;
        String jsonResult = analysisResult.find(rowKey);
        if (jsonResult != null) {
            return jsonResult;
        }
        List<double[]> doubles = hbaseService.findContentByParentId(datasetId).parallelStream()
                .map(TiffImage::new)
                .map(image -> image.getAllRegionGrayAverage(width, height))
                .collect(Collectors.toList());
        List<double[]> f = getTranspose(doubles);
        List<double[]> f0 = f.stream().map(s -> {
            double[] x = LongStream.rangeClosed(1, s.length).asDoubleStream().toArray();
            return ExponentFitUtil.getOneExpFuncValue(SignalFit.fitOneExponent(x, s), x);
        }).collect(Collectors.toList());
        try {
            return analysisResult.save(rowKey, objectMapper.writeValueAsString(new MultiRegionSignalDTO(f, f0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
