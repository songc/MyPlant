package com.songc.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MultipartFileService {
    String save(MultipartFile multipartFile);

    String save(List<MultipartFile> multipartFiles);
}
