package com.songc.controller;

import com.songc.dao.CellularRecordingMetaDao;
import com.songc.entity.meta.CellularRecordingMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
@RestController
@RequestMapping(value = "/user/{userId}/cellularRecordingMeta")
public class CellularRecordingMetaController {
    private CellularRecordingMetaDao cellularRecordingMetaDao;

    @Autowired
    public CellularRecordingMetaController(CellularRecordingMetaDao cellularRecordingMetaDao) {
        this.cellularRecordingMetaDao = cellularRecordingMetaDao;
    }

    @PostMapping
    public CellularRecordingMeta save(@RequestBody CellularRecordingMeta recordingMeta, @PathVariable(value = "userId") Long userId) {
        recordingMeta.setUserId(userId);
        return this.cellularRecordingMetaDao.save(recordingMeta);
    }

    @PutMapping
    public CellularRecordingMeta update(@RequestBody CellularRecordingMeta recordingMeta) {
        return this.cellularRecordingMetaDao.save(recordingMeta);
    }

    @GetMapping
    public List<CellularRecordingMeta> findByUserId(@PathVariable(value = "userId") Long userId) {
        return this.cellularRecordingMetaDao.findByUserId(userId);
    }

    @GetMapping(value = "/{id}")
    public CellularRecordingMeta findOne(@PathVariable(value = "id") Long id) {
        return cellularRecordingMetaDao.findOne(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        this.cellularRecordingMetaDao.delete(id);
    }
}
