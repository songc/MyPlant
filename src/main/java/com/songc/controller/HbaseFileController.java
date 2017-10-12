package com.songc.controller;

import com.songc.dto.HbaseFileWithContentDTO;
import com.songc.entity.data.StatusEnum;
import com.songc.service.HbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hbase")
public class HbaseFileController {

    private HbaseService hbaseService;

    @Autowired
    public HbaseFileController(HbaseService hbaseService) {
        this.hbaseService = hbaseService;
    }

    @GetMapping(value = "/{rowkey}")
    public HbaseFileWithContentDTO find(@PathVariable("rowkey") String rowKey) {
        return new HbaseFileWithContentDTO(hbaseService.find(rowKey));
    }

    @DeleteMapping(value = "/{rowkey}")
    public String delete(@PathVariable("rowkey") String rowKey) {
        hbaseService.delete(rowKey);
        return StatusEnum.SUCCESS.toString();
    }
}
