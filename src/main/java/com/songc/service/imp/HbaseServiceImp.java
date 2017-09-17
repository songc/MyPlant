package com.songc.service.imp;

import com.songc.dao.imp.HbaseDao;
import com.songc.entity.HbaseFile;
import com.songc.service.HbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HbaseServiceImp implements HbaseService {

    private HbaseDao hbaseDao;

    @Autowired
    public HbaseServiceImp(HbaseDao hbaseDao) {
        this.hbaseDao = hbaseDao;
    }

    @Override
    public HbaseFile save(HbaseFile hbaseFile) {
        return hbaseDao.save(hbaseFile);
    }

    @Override
    public HbaseFile find(String rowName) {
        return hbaseDao.find(rowName);
    }

    @Override
    public List<HbaseFile> findByParentId(Long parentId) {
        return hbaseDao.findByParentId(parentId);
    }

    @Override
    public void delete(String rowName) {
        hbaseDao.delete(rowName);
    }

    @Override
    public void deleteByParentId(Long parentId) {
        hbaseDao.deleteByParentId(parentId);
    }

    @Override
    public List<HbaseFile> save(List<HbaseFile> files) {
        return hbaseDao.save(files);
    }

}
