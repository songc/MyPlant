package com.songc.dto;

import com.songc.entity.HbaseFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HbaseFileDTO {
    private String rowKey;
    private Long parentId;
    private String name;

    public HbaseFileDTO() {
    }

    public HbaseFileDTO(HbaseFile hbaseFile) {
        rowKey = hbaseFile.getRowKey();
        parentId = hbaseFile.getParentId();
        name = hbaseFile.getName();
    }
}
