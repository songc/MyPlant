package com.songc.service;

import com.songc.entity.Folder;
import com.songc.entity.HbaseFile;

import java.util.List;

public interface FolderService {

    Folder save(Folder folder);

    Folder find(Long id);

    List<Folder> findSubFolder(Long parentId);

    Folder save(String name, Long parentId);

    List<HbaseFile> findSubFile(Long parentId);

    void delete(Long id);
}
