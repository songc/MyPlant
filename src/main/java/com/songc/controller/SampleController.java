package com.songc.controller;

import com.songc.dao.SampleDao;
import com.songc.entity.meta.Sample;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
@RestController
@RequestMapping(value = "/user/{userId}/sample")
public class SampleController {
    private SampleDao sampleDao;

    @Autowired
    public SampleController(SampleDao sampleDao) {
        this.sampleDao = sampleDao;
    }

    @ApiOperation(value = "Save sample information")
    @PostMapping
    public Sample save(@RequestBody Sample sample, @PathVariable(value = "userId") Long userId) {
        sample.setUserId(userId);
        return sampleDao.save(sample);
    }

    @ApiOperation(value = "Update sample information")
    @PutMapping
    public Sample update(@RequestBody Sample sample) {
        return sampleDao.save(sample);
    }

    @ApiOperation(value = "Find sample information by userId")
    @GetMapping
    public List<Sample> findByUserId(@PathVariable(value = "userId") Long userId) {
        return sampleDao.findByUserId(userId);
    }

    @ApiOperation(value = "Find sample information by id")
    @GetMapping(value = "/{id}")
    public Sample findOne(@PathVariable(value = "id") Long id) {
        return sampleDao.findOne(id);
    }

    @ApiOperation(value = "Delete sample information")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        sampleDao.delete(id);
    }
}
