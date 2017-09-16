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
    public HbaseFile save(Long parentId, String name, byte[] content) {
        return hbaseDao.save(parentId, name, content);
    }

    @Override
    public HbaseFile find(String rowName) {
        return hbaseDao.find(rowName);
    }

    @Override
    public void delete(String rowName) {
        hbaseDao.delete(rowName);
    }

    @Override
    public void deleteByParentId(Long parentId) {

    }

    @Override
    public void save(List<HbaseFile> files) {

    }

}
