package com.songc.controller;

import com.songc.entity.Folder;
import com.songc.entity.HbaseFile;
import com.songc.entity.data.StatusEnum;
import com.songc.service.FolderService;
import com.songc.service.MultipartFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderController {

    private FolderService folderService;
    private MultipartFileService multipartFileService;

    @Autowired
    public FolderController(FolderService folderService, MultipartFileService multipartFileService) {
        this.folderService = folderService;
        this.multipartFileService = multipartFileService;
    }

    @GetMapping(value = "/{id}")
    public Folder find(@PathVariable("id") Long id) {
        return folderService.find(id);
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable("id") Long id) {
        folderService.delete(id);
        return StatusEnum.SUCCESS.toString();
    }

    @PostMapping(value = "/{id}/subfolder")
    public Folder save(@RequestParam("name") String name, @PathVariable("id") Long parentId) {
        return folderService.save(name, parentId);
    }

    @GetMapping(value = "/{id}/subfolder")
    public List<Folder> findSubFolder(@PathVariable Long parentId) {
        return folderService.findSubFolder(parentId);
    }

    @PostMapping(value = "/{id}/singlefile")
    public HbaseFile save(@PathVariable("id") Long parentId, @RequestParam("file") MultipartFile multipartFile) {
        multipartFileService.save(parentId, multipartFile);
        return null;
    }

    @PostMapping(value = "/{id}/mulfile")
    public HbaseFile save(@PathVariable("id") Long parentId, @RequestParam("files") List<MultipartFile> multipartFiles) {
        multipartFileService.save(parentId, multipartFiles);
        return null;
    }

    @GetMapping(value = "/{id}/subfile")
    public List<HbaseFile> findSubFile(@PathVariable Long parentId) {
        return folderService.findSubFile(parentId);
    }
}
