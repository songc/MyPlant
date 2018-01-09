package com.songc.service;


/**
 * Created By @author songc
 * on 2017/11/7
 */
public interface ImageAnalysisService {
    String singleRegion(Long datasetId, int startX, int startY, int width, int height);

    String multiRegion(Long datasetId, int width, int height);
}
