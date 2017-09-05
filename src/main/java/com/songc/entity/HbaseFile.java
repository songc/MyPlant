package com.songc.entity;

import lombok.Data;

@Data
public class HbaseFile {
    public static long HF_ID = 0L;
    private Long parentId;
    private String name;
    private byte[] content;

    public HbaseFile(Long parentId,String name, byte[] content) {
        this.parentId = parentId;
        this.name = name;
        this.content = content;
        HbaseFile.HF_ID++;
    }
}
