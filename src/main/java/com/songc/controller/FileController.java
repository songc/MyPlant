package com.songc.controller;

import com.songc.entity.File;
import com.songc.service.FileService;
import com.songc.service.MultipartFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    private FileService fileService;
    private MultipartFileService multipartFileService;

    @Autowired
    public FileController(FileService fileService, MultipartFileService multipartFileService) {
        this.fileService = fileService;
        this.multipartFileService = multipartFileService;
    }

    @PostMapping(value = "/single")
    public String save(@RequestParam("file") MultipartFile multipartFile) {
        return multipartFileService.save(multipartFile);
    }

    @PostMapping(value = "/multi")
    public String save(@RequestParam("files") List<MultipartFile> multipartFiles) {
        return multipartFileService.save(multipartFiles);
    }

    @GetMapping
    public List<File> findByParentId(Long parentId) {
        return fileService.findByParentId(parentId);
    }
}
