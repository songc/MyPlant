package com.songc.entity;

import com.songc.entity.data.State;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by songc on 4/27/2017.
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Date updatedAt;

    private Long folderId;
}
