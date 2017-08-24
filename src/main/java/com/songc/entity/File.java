package com.songc.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by songc on 4/27/2017.
 */
@Entity
@Data
public class File {
    @Id
    @GeneratedValue
    private Long fileId;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long size;

//    @Column(nullable = false)
    private Long parentId;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp updatedAt;

}
