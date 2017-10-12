package com.songc.entity;

import lombok.Data;

@Data
public class HbaseFile {
    private String rowKey;
    private Long parentId;
    private String name;
    private byte[] content;

    public HbaseFile() {

    }

    public HbaseFile(Long parentId, String name, byte[] content) {
        this.parentId = parentId;
        this.name = name;
        this.content = content;
    }

    public HbaseFile(String rowKey, Long parentId, String name, byte[] content) {
        this.rowKey = rowKey;
        this.parentId = parentId;
        this.name = name;
        this.content = content;
    }
}
