package com.songc.dao;

import com.songc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by songc on 4/27/2017.
 */
public interface UserDao extends JpaRepository<User, Long> {
    User findUserByUsernameAndPassword(String name, String password);
}
