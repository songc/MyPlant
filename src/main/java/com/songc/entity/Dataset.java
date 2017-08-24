package com.songc.entity;

import com.songc.entity.data.State;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by songc on 4/27/2017.
 */
@Entity
@Data
public class Dataset{
    @Id
    @GeneratedValue
    private Long datasetId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private State state=State.open;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp updatedAt;

    private Long folderId;
}
