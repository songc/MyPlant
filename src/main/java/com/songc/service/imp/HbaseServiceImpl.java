package com.songc.service.imp;

import com.songc.dao.imp.HbaseDao;
import com.songc.dto.HbaseFileDTO;
import com.songc.dto.HbaseFileWithContentDTO;
import com.songc.entity.HbaseFile;
import com.songc.service.HbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HbaseServiceImpl implements HbaseService {

    private HbaseDao hbaseDao;

    @Autowired
    public HbaseServiceImpl(HbaseDao hbaseDao) {
        this.hbaseDao = hbaseDao;
    }

    @Override
    public HbaseFile save(HbaseFile hbaseFile) {
        return hbaseDao.save(hbaseFile);
    }

    @Override
    public List<HbaseFile> save(List<HbaseFile> files) {
        return hbaseDao.save(files);
    }

    @Override
    public HbaseFileWithContentDTO findContent(String rowKey) {
        return hbaseDao.find(rowKey);
    }

    @Override
    public List<HbaseFileDTO> findByParentId(Long parentId) {
        return hbaseDao.findByParentId(parentId);
    }

    @Override
    public List<HbaseFileWithContentDTO> findContentByParentId(Long parentId) {
        return hbaseDao.findContentByParentId(parentId);
    }

    @Override
    public void delete(String rowKey) {
        hbaseDao.delete(rowKey);
    }

    @Override
    public void deleteByParentId(Long parentId) {
        hbaseDao.deleteByParentId(parentId);
    }

    @Override
    public void delete(List<String> rowKeyList) {
        hbaseDao.delete(rowKeyList);
    }

}
