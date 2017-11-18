package com.songc.service;


import com.songc.dto.MultiRegionSignalDTO;
import com.songc.dto.SingleRegionSignalDTO;

/**
 * Created By @author songc
 * on 2017/11/7
 */
public interface ImageAnalysisService {
    SingleRegionSignalDTO singleRegion(Long datasetId, int startX, int startY, int width, int height);

    MultiRegionSignalDTO multiRegion(Long datasetId, int width, int height);
}
