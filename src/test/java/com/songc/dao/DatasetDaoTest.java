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
        List<Dataset> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i < 10) {
                list.add(new Dataset("Test", "songc", State.open, "songc", id));
            } else {
                list.add(new Dataset("Test", "songc", State.open, "songc", id2));
            }
        }
        datasetDao.save(list);
        Pageable pageable = new PageRequest(0, 4);
        Page<Dataset> datasetPage = datasetDao.findByUserIdIs(id, pageable);
        Page<Dataset> datasetsPage2 = datasetDao.findByUserIdIs(id2, pageable);
        assertEquals(3, datasetPage.getTotalPages());
        assertEquals(2, datasetsPage2.getTotalPages());
        assertEquals(10, datasetPage.getTotalElements());
        assertEquals(5, datasetsPage2.getTotalElements());
    }

    @Test
    public void update() throws InterruptedException {
        Long id = 200L;
        Dataset dataset = new Dataset();
        dataset.setUserId(id);
        dataset.setAuthor("songc");
        dataset.setName("dataset1");
        dataset.setDescription("something");
        Dataset dataset1 = datasetDao.save(dataset);
        dataset.setId(dataset1.getId());
        dataset.setAuthor("songc2");
        Dataset dataset2 = datasetDao.save(dataset);
        assertEquals(dataset1.getCreatedAt(), dataset2.getCreatedAt());
        assertEquals("songc2", dataset2.getAuthor());
    }

    @Test
    public void findByNameContaining() {
        Long id = 200L;
        Long id2 = 300L;
        List<Dataset> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i < 10) {
                list.add(new Dataset("Test1", "songc", State.open, "songc", id));
            } else {
                list.add(new Dataset("Test123", "songc", State.open, "songc", id2));
            }
        }
        datasetDao.save(list);
        Pageable pageable = new PageRequest(0, 10);
        Page<Dataset> datasetPage = datasetDao.findByNameContaining("Test", pageable);
        Page<Dataset> datasetsPage2 = datasetDao.findByNameContaining("Test12", pageable);
        assertEquals(5, datasetsPage2.getTotalElements());
        assertEquals(15, datasetPage.getTotalElements());
    }

}