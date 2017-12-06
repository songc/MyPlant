package com.songc.dto;

import com.songc.entity.HbaseFile;
import lombok.Data;

@Data
public class HbaseFileWithContentDTO {
    private String rowKey;
    private Long parentId;
    private String name;
    private String content;
    private Long sampleId;
    private Long imageMetaId;
    private Long cellularRecordingMetaId;
    private Long environmentId;

    public HbaseFileWithContentDTO() {
    }

    public HbaseFileWithContentDTO(HbaseFile hbaseFile) {
        rowKey = hbaseFile.getRowKey();
        parentId = hbaseFile.getParentId();
        name = hbaseFile.getName();
        content = new String(hbaseFile.getContent());
        sampleId = hbaseFile.getSampleId();
        imageMetaId = hbaseFile.getImageMetaId();
        cellularRecordingMetaId = hbaseFile.getCellularRecordingMetaId();
        environmentId = hbaseFile.getEnvironmentId();
    }
}
