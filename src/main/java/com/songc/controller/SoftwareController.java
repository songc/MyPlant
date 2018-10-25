package com.songc.controller;

import com.songc.dao.SoftwareDao;
import com.songc.entity.meta.Software;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
@RestController
@RequestMapping(value = "/user/{userId}/software")
public class SoftwareController {
    private SoftwareDao softwareDao;

    @Autowired
    public SoftwareController(SoftwareDao softwareDao) {
        this.softwareDao = softwareDao;
    }

    @ApiOperation(value = "Save software information")
    @PostMapping
    public Software save(@RequestBody Software software, @PathVariable(value = "userId") Long userId) {
        software.setUserId(userId);
        return softwareDao.save(software);
    }

    @ApiOperation(value = "Update software information")
    @PutMapping
    public Software update(@RequestBody Software software) {
        return softwareDao.save(software);
    }

    @ApiOperation(value = "Find software information by userId")
    @GetMapping
    public List<Software> findByUserId(@PathVariable(value = "userId") Long userId) {
        return softwareDao.findByUserId(userId);
    }

    @ApiOperation(value = "Find software information by id")
    @GetMapping(value = "/{id}")
    public Software findOne(@PathVariable(value = "id") Long id) {
        return softwareDao.findOne(id);
    }

    @ApiOperation(value = "Delete software information")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        softwareDao.delete(id);
    }
}
