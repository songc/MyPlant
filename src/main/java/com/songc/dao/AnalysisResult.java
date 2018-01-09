package com.songc.dao;

/**
 * Created By @author songc
 * on 2018/1/9
 */
public interface AnalysisResult {
    String save(String rowKey, String json);

    String find(String rowKey);
}
