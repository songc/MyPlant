package com.songc.service.imp;

import com.songc.dao.UserDao;
import com.songc.entity.User;
import com.songc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImpTest {

    @MockBean
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setUserId(1L);
        given(userDao.save(user)).willReturn(user);
        User user1 = userService.save(user);
        assertEquals(1L, (long) user1.getUserId());
    }

    @Test
    public void findUser() throws Exception {
        User user = new User();
        Long id = 100L;
        user.setUserId(id);
        given(userDao.findOne(id)).willReturn(user);
        User user1 = userService.findUser(id);
        assertEquals(id, user1.getUserId());
    }

    @Test
    public void findUserByUsernameAndPassword() throws Exception {
        User user = new User();
        user.setUsername("songc");
        user.setPassword("455");
        given(userDao.findUserByUsernameAndPassword(user.getUsername(), user.getPassword())).willReturn(user);
        User user1 = userService.findUserByUsernameAndPassword("songc", "455");
        assertEquals("songc", user1.getUsername());
        assertEquals("455", user1.getPassword());
    }

}