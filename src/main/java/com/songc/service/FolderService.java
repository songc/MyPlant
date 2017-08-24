package com.songc.service;

import com.songc.entity.File;
import com.songc.entity.Folder;

import java.util.List;

public interface FolderService {

    Long save(Folder folder);

    Folder find(Long id);

    List<Folder> findAllSubfolder(Long parentId);

    Long createSubfolder(String name, Long parentId);

    List<File> findAllSubFile(Long parentId);
}
