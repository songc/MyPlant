package com.songc.service.imp;

import com.songc.entity.HbaseFile;
import com.songc.service.HbaseService;
import com.songc.service.MultipartFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public List<HbaseFile> save(Long parentId, List<MultipartFile> multipartFiles) {
        List<HbaseFile> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
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
        return hbaseService.save(files);
    }
}
