package com.songc.dao;

import com.songc.entity.Dataset;
import com.songc.entity.data.State;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DatasetDaoTest {

    @Autowired
    private DatasetDao datasetDao;

    @Test
    public void findByUserId() throws Exception {
        Long id = 100L;
        Dataset dataset = new Dataset("Test", "songc", State.open, "songc", id);
        datasetDao.save(dataset);
        List<Dataset> datasets = datasetDao.findByUserId(id);
        assertEquals(1, datasets.size());
        assertEquals(id, datasets.get(0).getUserId());

    }

    @Test
    public void findByUserIdIs() throws Exception {
        Long id = 200L;
        Long id2 = 300L;
        Dataset dataset = new Dataset("Test", "songc", State.open, "songc", id);
        Dataset dataset2 = new Dataset("Test", "songc", State.open, "songc", id2);
        List<Dataset> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i < 10) {
                list.add(dataset);
            } else {
                list.add(dataset2);
            }
        }
        datasetDao.save(list);
        Pageable pageable = new PageRequest(0, 4);
        Page<Dataset> datasetPage = datasetDao.findByUserIdIs(id, pageable);
        Page<Dataset> datasetsPage2 = datasetDao.findByUserIdIs(id2, pageable);
        assertEquals(1, datasetPage.getTotalPages());
        assertEquals(1, datasetsPage2.getTotalPages());
        assertEquals(1, datasetPage.getTotalElements());
        assertEquals(1, datasetsPage2.getTotalElements());
    }

}