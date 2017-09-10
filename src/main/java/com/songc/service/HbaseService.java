package com.songc.service;

import com.songc.entity.HbaseFile;
import org.apache.hadoop.fs.Path;

import java.util.List;

public interface HbaseService{
    HbaseFile add(Long parentId, String name, byte[] content);

    HbaseFile find(String rowName);

    String delete(String rowName);
}
