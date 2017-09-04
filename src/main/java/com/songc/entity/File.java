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
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Date updatedAt;

}
