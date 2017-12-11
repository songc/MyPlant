package com.songc.dao;

import com.songc.entity.meta.IecMeta;
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
public class IecMetaDaoTest {
    private Long userId = 100L;

    @Autowired
    private IecMetaDao iecMetaDao;

    @Test
    public void findByUserId() throws Exception {
        List<IecMeta> recordingMetaList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            IecMeta recordingMeta = new IecMeta();
            recordingMeta.setUserId(userId);
            recordingMeta.setName("songc" + i);
            recordingMetaList.add(recordingMeta);
        }
        iecMetaDao.save(recordingMetaList);
        List<IecMeta> recordingMetaList1 = iecMetaDao.findByUserId(userId);
        List<IecMeta> recordingMetaList2 = iecMetaDao.findByUserId(userId - 1);
        assertEquals(recordingMetaList.size(), recordingMetaList1.size());
        assertEquals(recordingMetaList2.size(), 0);
    }

}