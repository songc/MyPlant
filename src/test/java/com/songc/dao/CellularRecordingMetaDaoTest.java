package com.songc.dao;

import com.songc.entity.meta.CellularRecordingMeta;
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
public class CellularRecordingMetaDaoTest {
    private Long userId = 100L;

    @Autowired
    private CellularRecordingMetaDao cellularRecordingMetaDao;

    @Test
    public void findByUserId() throws Exception {
        List<CellularRecordingMeta> recordingMetaList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CellularRecordingMeta recordingMeta = new CellularRecordingMeta();
            recordingMeta.setUserId(userId);
            recordingMeta.setName("songc" + i);
            recordingMetaList.add(recordingMeta);
        }
        cellularRecordingMetaDao.save(recordingMetaList);
        List<CellularRecordingMeta> recordingMetaList1 = cellularRecordingMetaDao.findByUserId(userId);
        List<CellularRecordingMeta> recordingMetaList2 = cellularRecordingMetaDao.findByUserId(userId - 1);
        assertEquals(recordingMetaList.size(), recordingMetaList1.size());
        assertEquals(recordingMetaList2.size(), 0);
    }

}