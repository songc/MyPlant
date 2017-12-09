package com.songc.service.imp;

import com.songc.dto.FileMeta;
import com.songc.entity.HbaseFile;
import com.songc.service.HbaseService;
import com.songc.service.MultipartFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MultipartFileServiceImpl implements MultipartFileService {

    private HbaseService hbaseService;


    @Autowired
    public MultipartFileServiceImpl(HbaseService hbaseService) {
        this.hbaseService = hbaseService;
    }

    @Override
    public HbaseFile save(Long parentId, MultipartFile multipartFile) {
        Assert.isTrue(!multipartFile.isEmpty(), "multipartFile can't be empty");
        HbaseFile file = new HbaseFile();
        file.setName(multipartFile.getOriginalFilename());
        file.setParentId(parentId);
        try {
            file.setContent(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hbaseService.save(file);
    }

    @Override
    public List<HbaseFile> save(Long parentId, List<MultipartFile> multipartFiles, FileMeta fileMeta) {
        List<HbaseFile> files = multipartFiles.stream()
                .filter(s -> !s.isEmpty())
                .map(multipartFile -> {
                    HbaseFile file = new HbaseFile();
                    file.setName(multipartFile.getOriginalFilename());
                    file.setParentId(parentId);
                    try {
                        file.setContent(multipartFile.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return file;
                }).map(file -> file.setFileMeta(fileMeta)).collect(Collectors.toList());
        return hbaseService.save(files);
    }
}
