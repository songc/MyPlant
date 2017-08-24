package com.songc.service;

import com.songc.entity.User;

/**
 * Created by songc on 4/27/2017.
 */
public interface UserService {

    Long addUser(User user);

    User findUser(Long id);

    User findUserByUsernameAndPassword(String username, String password);
}
