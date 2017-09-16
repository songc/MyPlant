package com.songc.service;

import com.songc.entity.HbaseFile;

import java.util.List;

public interface HbaseService{
    HbaseFile save(Long parentId, String name, byte[] content);

    HbaseFile find(String rowName);

    void delete(String rowName);

    void deleteByParentId(Long parentId);

    void save(List<HbaseFile> files);
}
