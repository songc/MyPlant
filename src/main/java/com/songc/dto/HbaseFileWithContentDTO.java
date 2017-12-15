package com.songc.dto;

import com.songc.entity.HbaseFile;
import lombok.Data;

@Data
public class HbaseFileWithContentDTO {
    private String rowKey;
    private byte[] content;
    private String name;

    public HbaseFileWithContentDTO() {
    }

    public HbaseFileWithContentDTO(HbaseFile hbaseFile) {
        name = hbaseFile.getName();
        rowKey = hbaseFile.getRowKey();
        content = hbaseFile.getContent();
    }
}
