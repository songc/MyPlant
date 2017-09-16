package com.songc.service.imp;

import com.songc.dao.UserDao;
import com.songc.entity.User;
import com.songc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by songc on 4/27/2017.
 */
@Service
public class UserServiceImp implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public User findUser(Long id) {
        return userDao.findOne(id);
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        return userDao.findUserByUsernameAndPassword(username, password);
    }
}
