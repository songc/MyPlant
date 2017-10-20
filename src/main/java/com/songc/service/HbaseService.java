package com.songc.service;

import com.songc.entity.HbaseFile;

import java.util.List;

public interface HbaseService{
    HbaseFile save(HbaseFile hbaseFile);

    List<HbaseFile> save(List<HbaseFile> files);

    HbaseFile find(String rowKey);

    List<HbaseFile> findByParentId(Long parentId);

    void delete(String rowKey);

    void deleteByParentId(Long parentId);

    void delete(List<String> rowKeyList);
}
