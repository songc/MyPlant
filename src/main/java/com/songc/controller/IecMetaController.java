package com.songc.controller;

import com.songc.dao.IecMetaDao;
import com.songc.entity.meta.IecMeta;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Save Iec file metadata")
    @PostMapping
    public IecMeta save(@RequestBody IecMeta recordingMeta, @PathVariable(value = "userId") Long userId) {
        recordingMeta.setUserId(userId);
        return this.iecMetaDao.save(recordingMeta);
    }

    @ApiOperation(value = "Update Iec file metadata")
    @PutMapping
    public IecMeta update(@RequestBody IecMeta recordingMeta) {
        return this.iecMetaDao.save(recordingMeta);
    }

    @ApiOperation(value = "Find Iec file metadata by userId")
    @GetMapping
    public List<IecMeta> findByUserId(@PathVariable(value = "userId") Long userId) {
        return this.iecMetaDao.findByUserId(userId);
    }

    @ApiOperation(value = "Find Iec file metadata by id")
    @GetMapping(value = "/{id}")
    public IecMeta findOne(@PathVariable(value = "id") Long id) {
        return iecMetaDao.findOne(id);
    }

    @ApiOperation(value = "Delete Iec file metadata")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        this.iecMetaDao.delete(id);
    }
}
