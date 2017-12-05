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
public class Environment {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    private Double light;
    private Double temperature;
    private Double humidity;
    private Double pressure;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    @Column(nullable = false)
    private Long userId;
}
