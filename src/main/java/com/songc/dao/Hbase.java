package com.songc.dao;

import com.songc.entity.HbaseFile;

import java.util.List;

public interface Hbase {
    HbaseFile find(String rowName);

    HbaseFile save(Long parentId,String name,byte[] content);

    List<HbaseFile> findAll();

    void delete(String rowName);

    String update(String rowName, HbaseFile hbaseFile);
}
