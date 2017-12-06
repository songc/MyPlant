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
    private Long sampleId;
    private Long imageMetaId;
    private Long cellularRecordingMetaId;
    private Long environmentId;

    public HbaseFileDTO() {
    }

    public HbaseFileDTO(HbaseFile hbaseFile) {
        rowKey = hbaseFile.getRowKey();
        parentId = hbaseFile.getParentId();
        name = hbaseFile.getName();
        sampleId = hbaseFile.getSampleId();
        imageMetaId = hbaseFile.getImageMetaId();
        cellularRecordingMetaId = hbaseFile.getCellularRecordingMetaId();
        environmentId = hbaseFile.getEnvironmentId();
    }
}
