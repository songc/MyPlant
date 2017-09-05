package com.songc.service.imp;

import com.songc.entity.File;
import com.songc.service.FileService;
import com.songc.service.MultipartFileService;
import com.songc.util.hadoop.FsShellUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class MultipartFileServiceImp implements MultipartFileService{

    private FileService fileService;

    @Value("${web.upload-path}")
    private String path;

    @Autowired
    public MultipartFileServiceImp(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public String save(MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            File file = new File();
            file.setName(multipartFile.getOriginalFilename());
            file.setSize(multipartFile.getSize());
            try {
                byte[] bytes = multipartFile.getBytes();
                FsShellUtil fsShellUtil = new FsShellUtil();
                fsShellUtil.writer(multipartFile.getOriginalFilename(), bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fileService.save(file);
        }
        return "fail, the file is empty" ;
    }

    @Override
    public String save(List<MultipartFile> multipartFiles) {

        BufferedOutputStream outputStream;
        List<File> files = new ArrayList<>();
        for (MultipartFile multipartFile: multipartFiles) {
            if (!multipartFile.isEmpty()) {
                File file = new File();
                file.setName(multipartFile.getOriginalFilename());
                file.setSize(multipartFile.getSize());
                try {
                    byte[] bytes = multipartFile.getBytes();
                    FsShellUtil fsShellUtil = new FsShellUtil();
                    fsShellUtil.writer(multipartFile.getOriginalFilename(), bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "fail " + e.getMessage();
                }
                files.add(file);
            }
        }
        return fileService.save(files);
    }

}
