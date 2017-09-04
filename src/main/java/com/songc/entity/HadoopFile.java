package com.songc.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by songc on 4/27/2017.
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class HadoopFile {
    @Id
    @GeneratedValue
    private Long hadoopFileId;

    @Column(nullable = false)
    private String path;

    private Integer childrenNum;

    @Column(nullable = false)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Date updatedAt;
}
