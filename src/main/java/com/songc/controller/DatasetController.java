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

    @ApiOperation(value = "Save dataset", notes = "")
    @PostMapping
    public Dataset save(@RequestBody Dataset dataset) {
        return datasetService.save(dataset);
    }

    @ApiOperation(value = "Get the dataset information")
    @GetMapping
    public Page<Dataset> getPageDataset(@RequestParam("number") Integer pageNumber, @RequestParam("size") Integer pageSize) {
        return datasetService.getPageDataset(pageNumber, pageSize);
    }

    @ApiOperation(value = "Search dataset information")
    @GetMapping(value = "/query")
    public Page<Dataset> search(@RequestParam("keyWord") String keyWord, @RequestParam("number") Integer pageNumber,
                                @RequestParam("size") Integer pageSize) {
        return datasetService.search(keyWord, pageNumber, pageSize);
    }


    @ApiOperation(value = "Modify dataset information")
    @PutMapping
    public Dataset update(@RequestBody Dataset dataset) {
        return datasetService.update(dataset);
    }

    @ApiOperation(value = "Get dataset information of the id")
    @GetMapping("/{id}")
    public Dataset findById(@PathVariable("id") Long id) {
        return datasetService.findOne(id);
    }

    @ApiOperation(value = "Delete the dataset id")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        datasetService.delete(id);
        return StatusEnum.SUCCESS.toString();
    }

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

    @ApiOperation(value = "Get the files of the dataset id")
    @GetMapping(value = "/{id}/file")
    public List<HbaseFileDTO> findFile(@PathVariable("id") Long parentId) {
        return datasetService.findFile(parentId);
    }

    @ApiOperation(value = "Download the zip of the dataset id")
    @GetMapping(value = "/{id}/zip")
    public void download(@PathVariable("id") Long parentId, @RequestParam("name") String datasetName, HttpServletResponse response) throws IOException {
        List<HbaseFileWithContentDTO> hbaseFileList = datasetService.findWithContentFile(parentId);
        HbaseUtil.convert2Zip(response, hbaseFileList, datasetName);
    }
}
