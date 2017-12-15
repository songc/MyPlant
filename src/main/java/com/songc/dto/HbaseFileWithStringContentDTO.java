package com.songc.dto;

import lombok.Data;

/**
 * Created By @author songc
 * on 2017/12/15
 */
@Data
public class HbaseFileWithStringContentDTO {
    private String rowKey;
    private String content;

    public HbaseFileWithStringContentDTO() {
    }

    public HbaseFileWithStringContentDTO(HbaseFileWithContentDTO hbaseFile) {
        rowKey = hbaseFile.getRowKey();
        content = new String(hbaseFile.getContent());
    }
}
