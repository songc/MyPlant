package com.songc.dto;

import com.songc.entity.HbaseFile;
import lombok.Data;

@Data
public class HbaseFileWithContentDTO {
    private String rowKey;
    private Long parentId;
    private String name;
    private String content;

    public HbaseFileWithContentDTO() {
    }

    public HbaseFileWithContentDTO(HbaseFile hbaseFile) {
        rowKey = hbaseFile.getRowKey();
        parentId = hbaseFile.getParentId();
        name = hbaseFile.getName();
        content = new String(hbaseFile.getContent());
    }
}
