package com.songc.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by songc on 4/27/2017.
 */
@Entity
@Data
public class Folder {
    @Id
    @GeneratedValue
    private Long folderId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long parentId;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp updatedAt;

}
