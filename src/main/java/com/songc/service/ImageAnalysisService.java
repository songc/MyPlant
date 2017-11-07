package com.songc.service;


import java.util.List;

/**
 * Created By @author songc
 * on 2017/11/7
 */
public interface ImageAnalysisService {
    int[] singleRegion(Long datasetId, int startX, int startY, int width, int height);

    List<int[]> multiRegion(Long datasetId, int width, int height);
}
