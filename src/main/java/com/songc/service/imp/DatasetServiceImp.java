package com.songc.service.imp;

import com.songc.dao.DatasetDao;
import com.songc.entity.Dataset;
import com.songc.entity.HbaseFile;
import com.songc.service.DatasetService;
import com.songc.service.HbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatasetServiceImp implements DatasetService {

    private DatasetDao datasetDao;

    private HbaseService hbaseService;

    @Autowired
    public DatasetServiceImp(DatasetDao datasetDao, HbaseService hbaseService) {
        this.datasetDao = datasetDao;
        this.hbaseService = hbaseService;
    }


    @Override
    public Dataset save(Dataset dataset) {
        return datasetDao.save(dataset);
    }

    @Override
    public Page<Dataset> getPageDataset(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        return datasetDao.findAll(pageable);
    }

    @Override
    public Dataset findOne(Long id) {
        return datasetDao.findOne(id);
    }

    @Override
    public List<Dataset> findByUserId(Long userId) {
        return datasetDao.findByUserId(userId);
    }

    @Override
    public void delete(Long id) {
        datasetDao.delete(id);
    }

    @Override
    public List<HbaseFile> findFile(Long parentId) {
        return hbaseService.findByParentId(parentId);
    }
}
