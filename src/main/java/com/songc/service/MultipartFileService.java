package com.songc.service;

import com.songc.entity.HbaseFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MultipartFileService {
    HbaseFile save(Long parentId, MultipartFile multipartFile);

    HbaseFile save(Long parentId, List<MultipartFile> multipartFiles);
}
