package com.songc.dao;

import com.songc.dto.HbaseFileDTO;
import com.songc.dto.HbaseFileWithContentDTO;
import com.songc.entity.HbaseFile;

import java.util.List;

public interface Hbase {
    HbaseFile save(HbaseFile hbaseFile);

    List<HbaseFile> save(List<HbaseFile> hbaseFiles);

    HbaseFileWithContentDTO find(String rowKey);

    List<HbaseFileDTO> findAll();

    List<HbaseFileDTO> findByParentId(Long parentId);

    List<HbaseFileWithContentDTO> findContentByParentId(Long parentId);

    List<byte[]> getRowKeysByParentId(Long parentId);

    void delete(String rowKey);

    void deleteByParentId(Long parentId);

    void delete(List<String> rowKeyList);

}
