package com.songc.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by songc on 4/27/2017.
 */
@Entity
@Data
public class HadoopFile {
    @Id
    @GeneratedValue
    private Long hadoopFileId;
    @Column(nullable = false)
    private String path;

    private Integer childrenNum;

    @Column(nullable = false)
    private Timestamp createdAt;
    @Column(nullable = false)
    private Timestamp updatedAt;
}
