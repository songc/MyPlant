package com.songc.controller;

import com.songc.dao.SoftwareDao;
import com.songc.entity.meta.Software;
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

    @PostMapping
    public Software save(@RequestBody Software software, @PathVariable(value = "userId") Long userId) {
        software.setUserId(userId);
        return softwareDao.save(software);
    }

    @PutMapping
    public Software update(@RequestBody Software software) {
        return softwareDao.save(software);
    }

    @GetMapping
    public List<Software> findByUserId(@PathVariable(value = "userId") Long userId) {
        return softwareDao.findByUserId(userId);
    }

    @GetMapping(value = "/{id}")
    public Software findOne(@PathVariable(value = "id") Long id) {
        return softwareDao.findOne(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        softwareDao.delete(id);
    }
}
