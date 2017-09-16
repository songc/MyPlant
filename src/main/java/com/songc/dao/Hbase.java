package com.songc.dao;

import com.songc.entity.HbaseFile;

import java.util.List;

public interface Hbase {
    HbaseFile save(Long parentId,String name,byte[] content);

    List<HbaseFile> save(List<HbaseFile> hbaseFiles);

    HbaseFile find(String rowName);

    List<HbaseFile> findAll();

    String update(String rowName, HbaseFile hbaseFile);

    void delete(String rowName);
}
