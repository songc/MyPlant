package com.songc.service.imp;

import com.songc.dao.FileDao;
import com.songc.entity.File;
import com.songc.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class FileServiceImp implements FileService {

    private FileDao fileDao;

    @Autowired
    public FileServiceImp(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public List<File> findByParentId(Long parentId) {
        return fileDao.findAllByParentId(parentId);
    }

    @Override
    public String save(File file) {
        file.setCreatedAt(Timestamp.from(Instant.now()));
        file.setUpdatedAt(Timestamp.from(Instant.now()));
        fileDao.save(file);
        return "SUCCESS";
    }

    @Override
    public String save(List<File> files) {
        for (File file : files) {
            file.setCreatedAt(Timestamp.from(Instant.now()));
            file.setUpdatedAt(Timestamp.from(Instant.now()));
        }
        fileDao.save(files);
        return "SUCCESS";
    }
}
