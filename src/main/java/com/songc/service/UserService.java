package com.songc.service;

import com.songc.entity.User;

/**
 * Created by songc on 4/27/2017.
 */
public interface UserService {

    User save(User user);

    User findUser(Long id);

    User findUserByUsernameAndPassword(String username, String password);
}
