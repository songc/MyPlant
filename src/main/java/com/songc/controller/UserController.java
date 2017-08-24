package com.songc.controller;


import com.songc.entity.User;
import com.songc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * Created by songc on 4/27/2017.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public User findUser(@PathVariable("userId") Long id) {
        return userService.findUser(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Long login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Assert.notNull(username, "username can't be null");
        Assert.notNull(password, "password can't be null");
        User user = userService.findUserByUsernameAndPassword(username, password);
        return user.getUserId();
    }
}
