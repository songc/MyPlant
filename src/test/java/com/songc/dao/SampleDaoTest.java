package com.songc.dao;

import com.songc.entity.meta.Sample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created By @author songc
 * on 2017/12/5
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class SampleDaoTest {

    private Long userId = 100L;

    @Autowired
    private SampleDao sampleDao;

    @Test
    public void findByUserId() throws Exception {
        List<Sample> sampleList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Sample sample = new Sample();
            sample.setUserId(userId);
            sample.setName("songc" + i);
            sampleList.add(sample);
        }
        sampleDao.save(sampleList);
        List<Sample> sampleList1 = sampleDao.findByUserId(userId);
        assertEquals(sampleList1.size(), sampleList.size());
        sampleDao.delete(sampleList1.get(0).getId());
        List<Sample> sampleList2 = sampleDao.findByUserId(userId);
        assertEquals(sampleList2.size(), sampleList1.size() - 1);
    }

}