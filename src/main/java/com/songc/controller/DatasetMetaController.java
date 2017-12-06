package com.songc.controller;

import com.songc.dao.DatasetMetaDao;
import com.songc.entity.meta.DatasetMeta;
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

    @PostMapping
    public DatasetMeta save(@RequestBody DatasetMeta datasetMeta, @PathVariable(value = "userId") Long userId) {
        datasetMeta.setUserId(userId);
        return datasetMetaDao.save(datasetMeta);
    }

    @PutMapping
    public DatasetMeta update(@RequestBody DatasetMeta datasetMeta) {
        return datasetMetaDao.save(datasetMeta);
    }

    @GetMapping
    public List<DatasetMeta> findByUserId(@PathVariable(value = "userId") Long userId) {
        return datasetMetaDao.findByUserId(userId);
    }

    @GetMapping(value = "/{id}")
    public DatasetMeta findOne(@PathVariable(value = "id") Long id) {
        return datasetMetaDao.findOne(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        datasetMetaDao.delete(id);
    }
}
