package com.songc.service.imp;

import com.songc.entity.HbaseFile;
import com.songc.service.HbaseService;
import com.songc.service.MultipartFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MultipartFileServiceImp implements MultipartFileService{

    private HbaseService hbaseService;

    @Value("${web.upload-path}")
    private String path;

    @Autowired
    public MultipartFileServiceImp(HbaseService hbaseService) {
        this.hbaseService = hbaseService;
    }

    @Override
    public HbaseFile save(Long parentId, MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            HbaseFile file = new HbaseFile();
            file.setName(multipartFile.getOriginalFilename());
            file.setParentId(parentId);
            try {
                file.setContent(multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            hbaseService.save(file.getParentId(), file.getName(), file.getContent());
        }
        return null;
    }

    @Override
    public HbaseFile save(Long parentId, List<MultipartFile> multipartFiles) {
        List<HbaseFile> files = new ArrayList<>();
        for (MultipartFile multipartFile: multipartFiles) {
            if (!multipartFile.isEmpty()) {
                HbaseFile file = new HbaseFile();
                file.setName(multipartFile.getOriginalFilename());
                file.setParentId(parentId);
                try {
                    file.setContent(multipartFile.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                files.add(file);
            }
        }
        hbaseService.save(files);
        return null;
    }
}
