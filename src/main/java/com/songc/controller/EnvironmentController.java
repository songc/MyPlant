package com.songc.controller;

import com.songc.dao.EnvironmentDao;
import com.songc.entity.meta.Environment;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/6
 */
@RestController
@RequestMapping(value = "/user/{userId}/environment")
public class EnvironmentController {

    private EnvironmentDao environmentDao;

    @Autowired
    public EnvironmentController(EnvironmentDao environmentDao) {
        this.environmentDao = environmentDao;
    }

    @ApiOperation(value = "Save environment information")
    @PostMapping
    public Environment save(@RequestBody Environment environment, @PathVariable(value = "userId") Long userId) {
        environment.setUserId(userId);
        return environmentDao.save(environment);
    }

    @ApiOperation(value = "Update environment information")
    @PutMapping
    public Environment update(@RequestBody Environment environment) {
        return environmentDao.save(environment);
    }

    @ApiOperation(value = "Find environment information by userId")
    @GetMapping
    public List<Environment> findByUserId(@PathVariable(value = "userId") Long userId) {
        return environmentDao.findByUserId(userId);
    }

    @ApiOperation(value = "Find environment information by id")
    @GetMapping(value = "/{id}")
    public Environment findOne(@PathVariable(value = "id") Long id) {
        return environmentDao.findOne(id);
    }

    @ApiOperation(value = "Delete environment information")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        environmentDao.delete(id);
    }
}
