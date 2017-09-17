package com.songc.dao;

import com.songc.entity.HbaseFile;

import java.util.List;

public interface Hbase {
    HbaseFile save(HbaseFile hbaseFile);

    List<HbaseFile> save(List<HbaseFile> hbaseFiles);

    HbaseFile find(String rowName);

    List<HbaseFile> findAll();

    List<HbaseFile> findByParentId(Long parentId);

    void delete(String rowName);

    void deleteByParentId(Long parentId);

    String update(String rowName, HbaseFile hbaseFile);
}
