package com.songc.service.imp;

import com.songc.core.image.TiffImage;
import com.songc.service.HbaseService;
import com.songc.service.ImageAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public int[] singleRegion(Long datasetId, int startX, int startY, int width, int height) {
        return hbaseService.findByParentId(datasetId).parallelStream()
                .map(TiffImage::new)
                .mapToInt(image -> image.getRegionGrayAverage(startX, startY, width, height))
                .toArray();
    }

    @Override
    public List<int[]> multiRegion(Long datasetId, int width, int height) {
        return hbaseService.findByParentId(datasetId).parallelStream()
                .map(TiffImage::new)
                .map(image -> image.getAllRegionGrayAverage(width, height))
                .collect(Collectors.toList());
    }
}
