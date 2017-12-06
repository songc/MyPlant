package com.songc.dao;

import com.songc.entity.meta.DatasetMeta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created By @author songc
 * on 2017/12/5
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DatasetMetaDaoTest {

    private Long userId = 100L;

    @Autowired
    private DatasetMetaDao datasetMetaDao;

    @Test
    public void findByUserId() throws Exception {
        List<DatasetMeta> datasetMetaList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DatasetMeta datasetMeta = new DatasetMeta();
            datasetMeta.setUserId(userId);
            datasetMeta.setName("songc" + i);
            datasetMetaList.add(datasetMeta);
        }
        datasetMetaDao.save(datasetMetaList);
        List<DatasetMeta> datasetMetaList1 = datasetMetaDao.findByUserId(userId);
        List<DatasetMeta> datasetMetaList2 = datasetMetaDao.findByUserId(userId - 1);
        assertEquals(datasetMetaList.size(), datasetMetaList1.size());
        assertEquals(datasetMetaList2.size(), 0);
        DatasetMeta datasetMeta = datasetMetaList1.get(0);
        assertNotNull(datasetMeta.getUpdatedAt());
        System.out.println(datasetMeta.getCreatedAt());
    }

}