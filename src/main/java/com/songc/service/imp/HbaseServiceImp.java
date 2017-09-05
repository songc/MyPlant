package com.songc.service.imp;

import com.songc.dao.HbaseFileDao;
import com.songc.entity.HbaseFile;
import com.songc.service.HbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HbaseServiceImp implements HbaseService {

    @Autowired
    private HbaseFileDao hbaseFileDao;

    @Override
    public HbaseFile addHbaseFile(Long parentId, String name, byte[] content) {
        return hbaseFileDao.save(parentId, name, content);
    }

    @Override
    public List<HbaseFile> findAll() {
        return hbaseFileDao.findAll();
    }
}
