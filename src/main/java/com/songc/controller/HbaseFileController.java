package com.songc.controller;

import com.songc.dto.HbaseFileWithStringContentDTO;
import com.songc.entity.data.StatusEnum;
import com.songc.service.HbaseService;
import com.songc.util.HbaseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/hbase")
public class HbaseFileController {

    private HbaseService hbaseService;

    @Autowired
    public HbaseFileController(HbaseService hbaseService) {
        this.hbaseService = hbaseService;
    }

    @ApiOperation(value = "Find the file with content in HBase")
    @GetMapping(value = "/{rowKey}")
    public HbaseFileWithStringContentDTO find(@PathVariable("rowKey") String rowKey) {
        return new HbaseFileWithStringContentDTO(hbaseService.findContent(rowKey));
    }

    @ApiOperation(value = "Find the picture in HBase")
    @GetMapping(value = "/png/{rowKey}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPng(@PathVariable("rowKey") String rowKey) throws IOException {
        return HbaseUtil.getPngBytes(hbaseService.findContent(rowKey));
    }

    @ApiOperation(value = "Delete the file in HBase")
    @DeleteMapping(value = "/{rowKey}")
    public String delete(@PathVariable("rowKey") String rowKey) {
        hbaseService.delete(rowKey);
        return StatusEnum.SUCCESS.toString();
    }

    @ApiOperation(value = "Delete multiply files in HBase")
    @DeleteMapping
    public String delete(@RequestBody List<String> rowKeyList) {
        hbaseService.delete(rowKeyList);
        return StatusEnum.SUCCESS.toString();
    }
}
