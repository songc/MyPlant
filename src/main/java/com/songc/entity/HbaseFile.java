package com.songc.entity;

import lombok.Data;

@Data
public class HbaseFile {
    private String rowKey;
    private Long parentId;
    private String name;
    private byte[] content;
    private Long sampleId;
    private Long imageMetaId;
    private Long cellularRecordingMetaId;
    private Long environmentId;

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
