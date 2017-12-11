package com.songc.controller;

import com.songc.dao.IecMetaDao;
import com.songc.entity.meta.IecMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
@RestController
@RequestMapping(value = "/user/{userId}/iecMeta")
public class IecMetaController {
    private IecMetaDao iecMetaDao;

    @Autowired
    public IecMetaController(IecMetaDao iecMetaDao) {
        this.iecMetaDao = iecMetaDao;
    }

    @PostMapping
    public IecMeta save(@RequestBody IecMeta recordingMeta, @PathVariable(value = "userId") Long userId) {
        recordingMeta.setUserId(userId);
        return this.iecMetaDao.save(recordingMeta);
    }

    @PutMapping
    public IecMeta update(@RequestBody IecMeta recordingMeta) {
        return this.iecMetaDao.save(recordingMeta);
    }

    @GetMapping
    public List<IecMeta> findByUserId(@PathVariable(value = "userId") Long userId) {
        return this.iecMetaDao.findByUserId(userId);
    }

    @GetMapping(value = "/{id}")
    public IecMeta findOne(@PathVariable(value = "id") Long id) {
        return iecMetaDao.findOne(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        this.iecMetaDao.delete(id);
    }
}
