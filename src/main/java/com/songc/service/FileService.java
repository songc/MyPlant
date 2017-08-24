package com.songc.service;

import com.songc.entity.File;

import java.util.List;

public interface FileService {

    List<File> findByParentId(Long ParentId);

    String save(File file);

    String save(List<File> files);
}
