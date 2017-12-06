package com.songc.controller;

import com.songc.dao.EnvironmentDao;
import com.songc.entity.meta.Environment;
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

    @PostMapping
    public Environment save(@RequestBody Environment environment, @PathVariable(value = "userId") Long userId) {
        environment.setUserId(userId);
        return environmentDao.save(environment);
    }

    @PutMapping
    public Environment update(@RequestBody Environment environment) {
        return environmentDao.save(environment);
    }

    @GetMapping
    public List<Environment> findByUserId(@PathVariable(value = "userId") Long userId) {
        return environmentDao.findByUserId(userId);
    }

    @GetMapping(value = "/{id}")
    public Environment findOne(@PathVariable(value = "id") Long id) {
        return environmentDao.findOne(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        environmentDao.delete(id);
    }
}
