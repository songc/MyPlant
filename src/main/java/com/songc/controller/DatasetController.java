package com.songc.controller;

import com.songc.dto.FileMeta;
import com.songc.dto.HbaseFileDTO;
import com.songc.dto.HbaseFileWithContentDTO;
import com.songc.entity.Dataset;
import com.songc.entity.HbaseFile;
import com.songc.entity.data.StatusEnum;
import com.songc.service.DatasetService;
import com.songc.service.MultipartFileService;
import com.songc.util.HbaseUtil;
import com.songc.util.MapperUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by songc on 4/27/2017.
 */
@RestController
@RequestMapping(value = "/dataset")
public class DatasetController {


    private DatasetService datasetService;
    private MultipartFileService multipartFileService;

    @Autowired
    public DatasetController(DatasetService datasetService, MultipartFileService multipartFileService) {
        this.datasetService = datasetService;
        this.multipartFileService = multipartFileService;
    }

    /**
     * 保存数据集信息到数据库 PS：没用的到这个方法
     *
     * @param dataset
     * @return 数据集信息
     */
    @ApiOperation(value = "Save dataset", notes = "")
    @PostMapping
    public Dataset save(@RequestBody Dataset dataset) {
        return datasetService.save(dataset);
    }

    /**
     * 获取指定页码的指定数量的数据集信息
     * @param pageNumber 分页页码
     * @param pageSize  分页的大小
     * @return 分页的数据集信息列表
     */
    @ApiOperation(value = "Get the dataset information")
    @GetMapping
    public Page<Dataset> getPageDataset(@RequestParam("number") Integer pageNumber, @RequestParam("size") Integer pageSize) {
        return datasetService.getPageDataset(pageNumber, pageSize);
    }

    /**
     * 根据关键词查找数据集信息，提供分页功能
     * @param keyWord 查找关键词
     * @param pageNumber 分页页码
     * @param pageSize 分页的大小
     * @return 分页的数据集信息列表
     */
    @ApiOperation(value = "Search dataset information")
    @GetMapping(value = "/query")
    public Page<Dataset> search(@RequestParam("keyWord") String keyWord, @RequestParam("number") Integer pageNumber,
                                @RequestParam("size") Integer pageSize) {
        return datasetService.search(keyWord, pageNumber, pageSize);
    }

    /**
     * 更新数据集信息
     * @param dataset 需要更新的数据集信息（包含id信息）
     * @return 更新后的数据集信息
     */
    @ApiOperation(value = "Modify dataset information")
    @PutMapping
    public Dataset update(@RequestBody Dataset dataset) {
        return datasetService.update(dataset);
    }

    /**
     * 根据数据集id查找数据集信息
     * @param id 数据集id
     * @return 指定id的数据集信息
     */
    @ApiOperation(value = "Get dataset information of the id")
    @GetMapping("/{id}")
    public Dataset findById(@PathVariable("id") Long id) {
        return datasetService.findOne(id);
    }

    /**
     * 删除指定id的数据集信息
     * @param id 数据集id
     * @return
     */
    @ApiOperation(value = "Delete the dataset id")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        datasetService.delete(id);
        return StatusEnum.SUCCESS.toString();
    }

    /**
     * 上传数据集的文件并保存到HBase中
     * @param parentId 数据集的id  （数据集的id的倒序是Hbase的RowKey的前缀）
     * @param multipartFiles 文件内容列表 （文件名，文件二进制内容等）
     * @param environmentId  文件环境元数据的id
     * @param softwareId  文件软件元数据的id
     * @param imageMetaId 图像文件元数据的id
     * @param iecMetaId  iec文件元数据的id
     * @param sampleId  文件样本元数据的id
     * @return 保存成功的文件列表的信息（不包含二进制内容）
     */
    @ApiOperation(value = "Save the files of dataset")
    @PostMapping(value = "/{id}/file")
    public List<HbaseFileDTO> save(@PathVariable("id") Long parentId,
                                   @RequestParam("files") List<MultipartFile> multipartFiles,
                                   @RequestParam(value = "environmentId", required = false) Long environmentId,
                                   @RequestParam(value = "softwareId", required = false) Long softwareId,
                                   @RequestParam(value = "imageMetaId", required = false) Long imageMetaId,
                                   @RequestParam(value = "iecMetaId", required = false) Long iecMetaId,
                                   @RequestParam(value = "sampleId", required = false) Long sampleId) {
        FileMeta fileMeta = new FileMeta();
        fileMeta.setSoftwareId(softwareId);
        fileMeta.setIecMetaId(iecMetaId);
        fileMeta.setEnvironmentId(environmentId);
        fileMeta.setImageMetaId(imageMetaId);
        fileMeta.setSampleId(sampleId);
        List<HbaseFile> hbaseFileList = multipartFileService.save(parentId, multipartFiles, fileMeta);
        return MapperUtil.convert(hbaseFileList);
    }

    /**
     * 根据数据集的id查找数据集的文件列表
     * @param parentId 数据集的id
     * @return 数据集的文件列表信息（包含二进制内容）
     */
    @ApiOperation(value = "Get the files of the dataset id")
    @GetMapping(value = "/{id}/file")
    public List<HbaseFileDTO> findFile(@PathVariable("id") Long parentId) {
        return datasetService.findFile(parentId);
    }

    /**
     * 下载整个数据集，数据集的zip压缩包
     * @param parentId 数据集id
     * @param datasetName 压缩包的文件名
     * @param response http请求对象
     * @throws IOException
     */
    @ApiOperation(value = "Download the zip of the dataset id")
    @GetMapping(value = "/{id}/zip")
    public void download(@PathVariable("id") Long parentId, @RequestParam("name") String datasetName, HttpServletResponse response) throws IOException {
        List<HbaseFileWithContentDTO> hbaseFileList = datasetService.findWithContentFile(parentId);
        HbaseUtil.convert2Zip(response, hbaseFileList, datasetName);
    }
}
