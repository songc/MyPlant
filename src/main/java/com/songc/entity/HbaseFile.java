package com.songc.entity;

import com.songc.dto.FileMeta;
import lombok.Data;

@Data
public class HbaseFile {
    private String rowKey;
    private Long parentId;
    private String name;
    private byte[] content;
    private Long sampleId;
    private Long imageMetaId;
    private Long iecMetaId;
    private Long environmentId;
    private Long softwareId;

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

    public HbaseFile setFileMeta(FileMeta fileMeta) {
        if (fileMeta.getEnvironmentId() != null) {
            this.setEnvironmentId(fileMeta.getEnvironmentId());
        }
        if (fileMeta.getIecMetaId() != null) {
            this.setIecMetaId(fileMeta.getIecMetaId());
        }
        if (fileMeta.getImageMetaId() != null) {
            this.setImageMetaId(fileMeta.getImageMetaId());
        }
        if (fileMeta.getSoftwareId() != null) {
            this.setSoftwareId(fileMeta.getSoftwareId());
        }
        if (fileMeta.getSampleId() != null) {
            this.setSampleId(fileMeta.getSampleId());
        }
        return this;
    }
}
