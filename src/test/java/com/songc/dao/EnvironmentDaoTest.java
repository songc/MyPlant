package com.songc.dao;

import com.songc.entity.meta.Environment;
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
public class EnvironmentDaoTest {

    private Long userId = 100L;

    @Autowired
    private EnvironmentDao environmentDao;

    @Test
    public void findByUserId() throws Exception {
        List<Environment> environmentList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Environment environment = new Environment();
            environment.setUserId(userId);
            environment.setName("songc" + i);
            environmentList.add(environment);
        }
        environmentDao.save(environmentList);
        List<Environment> environmentList1 = environmentDao.findByUserId(userId);
        List<Environment> environmentList2 = environmentDao.findByUserId(userId - 1);
        assertEquals(environmentList.size(), environmentList1.size());
        assertEquals(environmentList2.size(), 0);
    }

}