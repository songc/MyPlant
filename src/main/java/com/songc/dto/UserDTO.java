package com.songc.dto;

import com.songc.entity.User;
import com.songc.entity.data.SexEnum;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private SexEnum sex;
    private String address;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.sex = user.getSex();
        this.address = user.getAddress();
    }
}
