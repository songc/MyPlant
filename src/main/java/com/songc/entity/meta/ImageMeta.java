package com.songc.entity.meta;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created By @author songc
 * on 2017/12/4
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ImageMeta {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    private Date startAt;
    private Double duration;
    private Integer frameRate;
    private String recordPosition;
    private String recordArea;
    private String signalType;
    private String stimulateType;
    private String stimulateMaterial;
    private String stimulateDetail;
    private String format;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    @Column(nullable = false)
    private Long userId;
}
