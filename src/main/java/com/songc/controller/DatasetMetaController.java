package com.songc.controller;

import com.songc.dao.DatasetMetaDao;
import com.songc.entity.meta.DatasetMeta;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
@RestController
@RequestMapping(value = "/user/{userId}/datasetMeta")
public class DatasetMetaController {
    private DatasetMetaDao datasetMetaDao;

    @Autowired
    public DatasetMetaController(DatasetMetaDao datasetMetaDao) {
        this.datasetMetaDao = datasetMetaDao;
    }

    @ApiOperation(value = "Save dataset metadata ")
    @PostMapping
    public DatasetMeta save(@RequestBody DatasetMeta datasetMeta, @PathVariable(value = "userId") Long userId) {
        datasetMeta.setUserId(userId);
        return datasetMetaDao.save(datasetMeta);
    }

    @ApiOperation(value = "Update dataset metadata")
    @PutMapping
    public DatasetMeta update(@RequestBody DatasetMeta datasetMeta) {
        return datasetMetaDao.save(datasetMeta);
    }

    @ApiOperation(value = "Find dataset metadata by userId")
    @GetMapping
    public List<DatasetMeta> findByUserId(@PathVariable(value = "userId") Long userId) {
        return datasetMetaDao.findByUserId(userId);
    }

    @ApiOperation(value = "Find dataset metadata by id")
    @GetMapping(value = "/{id}")
    public DatasetMeta findOne(@PathVariable(value = "id") Long id) {
        return datasetMetaDao.findOne(id);
    }

    @ApiOperation(value = "Delete dataset metadata")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        datasetMetaDao.delete(id);
    }
}
