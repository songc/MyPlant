package com.songc.controller;


import com.songc.dto.UserDTO;
import com.songc.entity.Dataset;
import com.songc.entity.User;
import com.songc.service.DatasetService;
import com.songc.service.UserService;
import io.swagger.annotations.ApiOperation;
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

    /**
     * 用户注册，保存用户信息到数据库中，并返回用户信息。
     *
     * @param user 用户的注册信息
     * @return userDTO 用户的部分信息（不包括密码）。
     */
    @ApiOperation(value = "Save user information")
    @PostMapping
    public UserDTO save(@RequestBody User user) {
        return new UserDTO(userService.save(user));
    }

    /**
     * 根据用户id查找用户
     * @param id  用户id
     * @return userDTO 用户部分信息（不包括密码）
     */
    @ApiOperation(value = "Find user information by userId")
    @GetMapping(value = "/{id}")
    public UserDTO findUser(@PathVariable("id") Long id) {
        Assert.notNull(id, "id can't be null");
        return new UserDTO(userService.findUser(id));
    }

    /**
     * 用户登录，更加用户名和密码查找用户。
     * @param username
     * @param password
     * @return 成功返回用户部分信息，失败报错。
     */
    @ApiOperation(value = "User Login in")
    @PostMapping(value = "/login")
    public UserDTO login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Assert.notNull(username, "username can't be null");
        Assert.notNull(password, "password can't be null");
        return new UserDTO(userService.findUserByUsernameAndPassword(username, password));
    }

    /**
     * 根据用户id查找用户的数据集信息，提供分页查询的功能。
     * @param id 用户id
     * @param pageNumber 分页的起始页
     * @param pageSize  分页的大小
     * @return 返回指定页码的指定数量的数据集信息。
     */
    @ApiOperation(value = "Get dataset of the userId")
    @GetMapping(value = "/{id}/dataset")
    public Page<Dataset> findDataset(@PathVariable("id") Long id, @RequestParam("number") Integer pageNumber,
                                     @RequestParam("size") Integer pageSize) {
        Assert.notNull(id, "id can't be null");
        return datasetService.getPageDatasetByUserId(id, pageNumber, pageSize);
    }

    /**
     * 保存用户创建的数据集信息到数据库
     * @param id 用户id
     * @param dataset 数据集信息
     * @return 成功则返回数据集信息（包含系统生成的数据集Id信息）
     */
    @ApiOperation(value = "Create a dataset belong to the userId")
    @PostMapping(value ="/{id}/dataset")
    public Dataset save(@PathVariable("id") Long id, @RequestBody Dataset dataset) {
        Assert.notNull(id, "id can't be null");
        Assert.notNull(dataset,"dataset can't be null");
        dataset.setUserId(id);
        return datasetService.save(dataset);
    }
}
