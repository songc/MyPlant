package com.songc.controller;

import com.songc.entity.File;
import com.songc.entity.Folder;
import com.songc.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderController {

    private FolderService folderService;

    @Autowired
    public FolderController(@RequestBody FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping
    public Long createSubFolder(@RequestParam("name") String name, @RequestParam("parentId") Long parentId) {
        return folderService.createSubfolder(name, parentId);
    }

    @GetMapping
    public Folder findFolder(@RequestParam Long id) {
        return folderService.find(id);
    }
    @GetMapping(value = "/subfolder")
    public List<Folder> findAllSubFolder(@RequestParam Long parentId) {
        return folderService.findAllSubfolder(parentId);
    }

    @GetMapping(value = "/subfile")
    public List<File> findAllSubFile(@RequestParam Long parentId){
        return folderService.findAllSubFile(parentId);
    }
}
