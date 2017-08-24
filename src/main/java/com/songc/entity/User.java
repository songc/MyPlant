package com.songc.entity;

import com.songc.entity.data.Sex;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by songc on 4/26/2017.
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private Long userId;

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
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp updatedAt;

}
