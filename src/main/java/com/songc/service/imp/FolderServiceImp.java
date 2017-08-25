package com.songc.service.imp;

import com.songc.dao.FolderDao;
import com.songc.entity.File;
import com.songc.entity.Folder;
import com.songc.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class FolderServiceImp implements FolderService {
    @Autowired
    private FolderDao folderDao;

    @Override
    public Long save(Folder folder) {
//        folder.setCreatedAt(Timestamp.from(Instant.now()));
//        folder.setUpdatedAt(Timestamp.from(Instant.now()));
        return folderDao.save(folder).getFolderId();
    }

    @Override
    public Folder find(Long id) {
        return folderDao.findOne(id);
    }

    @Override
    public List<Folder> findAllSubfolder(Long parentId) {
        return folderDao.findAllByParentId(parentId);
    }

    @Override
    public Long createSubfolder(String name, Long parentId) {
        Folder folder = new Folder();
        folder.setName(name);
        folder.setParentId(parentId);
        return this.save(folder);
    }

    @Override
    public List<File> findAllSubFile(Long parentId) {
        return null;
    }
}
