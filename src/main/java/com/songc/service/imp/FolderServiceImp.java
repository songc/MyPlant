package com.songc.service.imp;

import com.songc.dao.FolderDao;
import com.songc.entity.Folder;
import com.songc.entity.HbaseFile;
import com.songc.service.FolderService;
import com.songc.service.HbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderServiceImp implements FolderService {

    private FolderDao folderDao;
    private HbaseService hbaseService;

    @Autowired
    public FolderServiceImp(FolderDao folderDao, HbaseService hbaseService) {
        this.folderDao = folderDao;
        this.hbaseService = hbaseService;
    }

    @Override
    public Folder save(Folder folder) {
        return folderDao.save(folder);
    }

    @Override
    public Folder find(Long id) {
        return folderDao.findOne(id);
    }

    @Override
    public List<Folder> findSubFolder(Long parentId) {
        return folderDao.findAllByParentId(parentId);
    }

    @Override
    public Folder save(String name, Long parentId) {
        Folder folder = new Folder();
        folder.setName(name);
        folder.setParentId(parentId);
        return this.save(folder);
    }

    @Override
    public List<HbaseFile> findSubFile(Long parentId) {
        return hbaseService.findByParentId(parentId);
    }

    @Override
    public void delete(Long id) {
        deleteChidren(id);
        folderDao.delete(id);
    }

    /**
     * recursive delete sub folder
     *
     * @param id parentId
     */
    private void deleteChidren(Long id) {
        List<Folder> folders = folderDao.findAllByParentId(id);
        hbaseService.deleteByParentId(id);
        for (Folder folder : folders) {
            deleteChidren(folder.getFolderId());
        }
        folderDao.delete(folders);
    }
}
