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

    /**
     * 根据rowKey查找HBase中的文件，返回文件内容。
     *
     * @param rowKey 文件的rowKey，索引
     * @return HbaseFileWithStringContent 文本文件的字符串内容
     */
    @ApiOperation(value = "Find the file with content in HBase")
    @GetMapping(value = "/{rowKey}")
    public HbaseFileWithStringContentDTO find(@PathVariable("rowKey") String rowKey) {
        return new HbaseFileWithStringContentDTO(hbaseService.findContent(rowKey));
    }

    /**
     * 根据rowKey获取图像文件的Png格式图片
     * @param rowKey 图像文件的rowKey索引
     * @return png图片的二进制内容
     * @throws IOException
     */
    @ApiOperation(value = "Find the picture in HBase")
    @GetMapping(value = "/png/{rowKey}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPng(@PathVariable("rowKey") String rowKey) throws IOException {
        return HbaseUtil.getPngBytes(hbaseService.findContent(rowKey));
    }

    /**
     * 根据rowKey删除单个文件
     * @param rowKey Hbase的索引
     * @return
     */
    @ApiOperation(value = "Delete the file in HBase")
    @DeleteMapping(value = "/{rowKey}")
    public String delete(@PathVariable("rowKey") String rowKey) {
        hbaseService.delete(rowKey);
        return StatusEnum.SUCCESS.toString();
    }

    /**
     * 根据rowKey列表，删除多个文件
     * @param rowKeyList 需要删除的文件的rowKey列表
     * @return
     */
    @ApiOperation(value = "Delete multiply files in HBase")
    @DeleteMapping
    public String delete(@RequestBody List<String> rowKeyList) {
        hbaseService.delete(rowKeyList);
        return StatusEnum.SUCCESS.toString();
    }
}
