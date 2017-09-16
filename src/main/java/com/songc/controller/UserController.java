package com.songc.controller;


import com.songc.entity.Dataset;
import com.songc.entity.User;
import com.songc.service.DatasetService;
import com.songc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by songc on 4/27/2017.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private UserService userService;
    private DatasetService datasetService;

    @Autowired
    public UserController(UserService userService, DatasetService datasetService) {
        this.userService = userService;
        this.datasetService = datasetService;
    }

    @PostMapping
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping(value = "/{id}")
    public User findUser(@PathVariable("id") Long id) {
        Assert.notNull(id,"userId can't be null");
        return userService.findUser(id);
    }

    @PostMapping(value = "/login")
    public User login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Assert.notNull(username, "username can't be null");
        Assert.notNull(password, "password can't be null");
        return userService.findUserByUsernameAndPassword(username, password);
    }

    @GetMapping(value = "/{id}/dataset")
    public List<Dataset> findDataset(@PathVariable("id") Long id) {
        Assert.notNull(id, "userId can't be null");
        return datasetService.findByUserId(id);
    }

    @PostMapping(value ="/{id}/dataset")
    public Dataset save(@PathVariable("id") Long id, @RequestBody Dataset dataset) {
        Assert.notNull(id, "userId can't be null");
        Assert.notNull(dataset,"dataset can't be null");
        dataset.setUserId(id);
        return datasetService.save(dataset);
    }
}
