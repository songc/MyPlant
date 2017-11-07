package com.songc.dto;

import com.songc.entity.HbaseFile;
import lombok.Data;

/**
 * Created by @author songc
 */
@Data
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
