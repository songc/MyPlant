package com.songc.controller;


import com.songc.dto.UserDTO;
import com.songc.entity.Dataset;
import com.songc.entity.User;
import com.songc.service.DatasetService;
import com.songc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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
    public UserDTO save(@RequestBody User user) {
        return new UserDTO(userService.save(user));
    }

    @GetMapping(value = "/{id}")
    public UserDTO findUser(@PathVariable("id") Long id) {
        Assert.notNull(id, "id can't be null");
        return new UserDTO(userService.findUser(id));
    }

    @PostMapping(value = "/login")
    public UserDTO login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Assert.notNull(username, "username can't be null");
        Assert.notNull(password, "password can't be null");
        return new UserDTO(userService.findUserByUsernameAndPassword(username, password));
    }

    @GetMapping(value = "/{id}/dataset")
    public Page<Dataset> findDataset(@PathVariable("id") Long id, @RequestParam("number") Integer pageNumber,
                                     @RequestParam("size") Integer pageSize) {
        Assert.notNull(id, "id can't be null");
        return datasetService.getPageDatasetByUserId(id, pageNumber, pageSize);
    }

    @PostMapping(value ="/{id}/dataset")
    public Dataset save(@PathVariable("id") Long id, @RequestBody Dataset dataset) {
        Assert.notNull(id, "id can't be null");
        Assert.notNull(dataset,"dataset can't be null");
        dataset.setUserId(id);
        return datasetService.save(dataset);
    }
}
