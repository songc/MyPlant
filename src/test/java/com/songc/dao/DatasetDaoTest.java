package com.songc.dao;

import com.songc.entity.Dataset;
import com.songc.entity.data.State;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
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

}