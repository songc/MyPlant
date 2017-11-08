package com.songc.dao;

import com.songc.entity.User;
import com.songc.entity.data.SexEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDaoTest {


    @Autowired
    private UserDao userDao;

    @Test
    public void findUserByUsernameAndPassword() throws Exception {
        User user = new User();
        String username = "songc";
        String password = "443502355";
        String email = "443502355@qq.com";
        String address = "beijing";
        SexEnum sex = SexEnum.MAN;
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setAddress(address);
        user.setSex(sex);
        userDao.save(user);
        User user1 = userDao.findUserByUsernameAndPassword(username, password);
        assertEquals(username, user1.getUsername());
        assertEquals(password, user1.getPassword());
        assertEquals(user1.getCreatedAt(), user1.getUpdatedAt());
    }

}