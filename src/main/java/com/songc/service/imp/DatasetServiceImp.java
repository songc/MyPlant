package com.songc.service.imp;

import com.songc.dao.DatasetDao;
import com.songc.entity.Dataset;
import com.songc.entity.Folder;
import com.songc.service.DatasetService;
import com.songc.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class DatasetServiceImp implements DatasetService {

    private static final Long  DEFAULT_Parent_ID = (long)0;

    private DatasetDao datasetDao;


    private FolderService folderService;

    @Autowired
    public DatasetServiceImp(DatasetDao datasetDao, FolderService folderService) {
        this.datasetDao = datasetDao;
        this.folderService = folderService;
    }


    @Override
    public Dataset save(Dataset dataset) {

        Folder folder = new Folder();
        folder.setName(dataset.getName());
        folder.setParentId(DEFAULT_Parent_ID);
        Long folderId = folderService.save(folder);

        dataset.setFolderId(folderId);
        return datasetDao.save(dataset);
    }

    @Override
    public Page<Dataset> getPageDataset(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        return datasetDao.findAll(pageable);
    }

    @Override
    public List<Dataset> findByUserId(Long userId) {
        return null;
    }

    @Override
    public String delete(Long id) {
        return null;
    }
}
