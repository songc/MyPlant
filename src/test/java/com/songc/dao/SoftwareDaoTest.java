package com.songc.dao;

import com.songc.entity.meta.Software;
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
public class SoftwareDaoTest {

    private Long userId = 100L;
    @Autowired
    private SoftwareDao softwareDao;

    @Test
    public void findByUserId() throws Exception {
        List<Software> softwareList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Software software = new Software();
            software.setUserId(userId);
            software.setName("songc" + 1);
            softwareList.add(software);
        }
        softwareDao.save(softwareList);
        List<Software> softwareList1 = softwareDao.findByUserId(userId);
        assertEquals(softwareList1.size(), softwareList.size());
        softwareDao.delete(softwareList1.get(0).getId());
        List<Software> softwareList2 = softwareDao.findByUserId(userId);
        assertEquals(softwareList2.size(), softwareList1.size() - 1);
    }

}