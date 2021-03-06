package com.songc.controller;

import com.songc.dao.EquipmentDao;
import com.songc.entity.meta.Equipment;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
@RestController
@RequestMapping(value = "/user/{userId}/equipment")
public class EquipmentController {
    private EquipmentDao equipmentDao;

    @Autowired
    public EquipmentController(EquipmentDao equipmentDao) {
        this.equipmentDao = equipmentDao;
    }

    @ApiOperation(value = "Save Equipment information")
    @PostMapping
    public Equipment save(@RequestBody Equipment equipment, @PathVariable(value = "userId") Long userId) {
        equipment.setUserId(userId);
        return equipmentDao.save(equipment);
    }

    @ApiOperation(value = "Update Equipment information")
    @PutMapping
    public Equipment update(@RequestBody Equipment equipment) {
        return equipmentDao.save(equipment);
    }

    @ApiOperation(value = "Find Equipment information by userId")
    @GetMapping
    public List<Equipment> findByUserId(@PathVariable(value = "userId") Long userId) {
        return equipmentDao.findByUserId(userId);
    }

    @ApiOperation(value = "Find Equipment information by id")
    @GetMapping(value = "/{id}")
    public Equipment findOne(@PathVariable(value = "id") Long id) {
        return equipmentDao.findOne(id);
    }

    @ApiOperation(value = "Delete Equipment information")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        equipmentDao.delete(id);
    }
}
