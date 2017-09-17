package com.songc.entity;

import com.songc.entity.data.Sex;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by songc on 4/26/2017.
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue
    private Long userId;

    public User() {
    }

    public User(String username, String password, String email, Sex sex, String address) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.sex = sex;
        this.address = address;
    }

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private Sex sex;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Date updatedAt;

}
