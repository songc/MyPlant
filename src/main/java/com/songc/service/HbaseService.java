package com.songc.service;

import com.songc.dto.HbaseFileDTO;
import com.songc.dto.HbaseFileWithContentDTO;
import com.songc.entity.HbaseFile;

import java.util.List;

public interface HbaseService{
    HbaseFile save(HbaseFile hbaseFile);

    List<HbaseFile> save(List<HbaseFile> files);

    HbaseFileWithContentDTO findContent(String rowKey);

    List<HbaseFileDTO> findByParentId(Long parentId);

    List<HbaseFileWithContentDTO> findContentByParentId(Long parentId);

    void delete(String rowKey);

    void deleteByParentId(Long parentId);

    void delete(List<String> rowKeyList);
}
