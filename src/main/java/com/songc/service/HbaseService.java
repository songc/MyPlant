package com.songc.service;

import com.songc.entity.HbaseFile;

import java.util.List;

public interface HbaseService{
    HbaseFile save(HbaseFile hbaseFile);

    HbaseFile find(String rowName);

    List<HbaseFile> findByParentId(Long parentId);

    void delete(String rowName);

    void deleteByParentId(Long parentId);

    List<HbaseFile> save(List<HbaseFile> files);
}
