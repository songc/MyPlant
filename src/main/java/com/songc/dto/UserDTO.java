package com.songc.dto;

import com.songc.entity.data.Sex;

public class UserDTO {
    private Long Id;
    private String username;
    private String email;
    private Sex sex;
    private String address;

    public UserDTO(Long id, String username, String email, Sex sex, String address) {
        Id = id;
        this.username = username;
        this.email = email;
        this.sex = sex;
        this.address = address;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
