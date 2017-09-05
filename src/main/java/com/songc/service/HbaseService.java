package com.songc.service;

import com.songc.entity.HbaseFile;
import org.apache.hadoop.fs.Path;

import java.util.List;

public interface HbaseService{
    HbaseFile addHbaseFile(Long parentId, String name, byte[] content);

    List<HbaseFile> findAll();
}
