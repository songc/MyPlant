package com.songc.entity;

import com.songc.entity.data.DatasetModeEnum;
import com.songc.entity.data.DatasetTypeEnum;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by @author songc on 4/27/2017.
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Dataset{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DatasetTypeEnum type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DatasetModeEnum state = DatasetModeEnum.OPEN;

    @Column(columnDefinition = "varchar(500)")
    private String description;

    @Column(nullable = false)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Date updatedAt;

    @Column(nullable = false)
    private Long userId;

    private Long datasetMetaId;

    private Long equipmentId;


    public Dataset(String name, String author, DatasetTypeEnum type, DatasetModeEnum state, String description, Long userId) {
        this.name = name;
        this.author = author;
        this.type = type;
        this.state = state;
        this.description = description;
        this.userId = userId;
    }

    public Dataset() {

    }
}
