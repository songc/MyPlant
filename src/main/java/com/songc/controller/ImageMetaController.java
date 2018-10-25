package com.songc.controller;

import com.songc.dao.ImageMetaDao;
import com.songc.entity.meta.ImageMeta;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created By @author songc
 * on 2017/12/4
 */
@RestController
@RequestMapping(value = "/user/{userId}/imageMeta")
public class ImageMetaController {
    private ImageMetaDao imageMetaDao;

    @Autowired
    public ImageMetaController(ImageMetaDao imageMetaDao) {
        this.imageMetaDao = imageMetaDao;
    }

    @ApiOperation(value = "Save image metadata")
    @PostMapping
    public ImageMeta save(@RequestBody ImageMeta imageMeta, @PathVariable(value = "userId") Long userId) {
        imageMeta.setUserId(userId);
        return imageMetaDao.save(imageMeta);
    }

    @ApiOperation(value = "Update image metadata")
    @PutMapping
    public ImageMeta update(ImageMeta imageMeta) {
        return imageMetaDao.save(imageMeta);
    }

    @ApiOperation(value = "Find image metadata by userId")
    @GetMapping
    public List<ImageMeta> findByUserId(@PathVariable("userId") Long userId) {
        return imageMetaDao.findByUserId(userId);
    }

    @ApiOperation(value = "Find image metadata by id")
    @GetMapping(value = "/{id}")
    public ImageMeta findOne(@PathVariable(value = "id") Long id) {
        return imageMetaDao.findOne(id);
    }

    @ApiOperation(value = "Delete image metadata")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        imageMetaDao.delete(id);
    }
}
