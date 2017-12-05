package com.songc.dao;

import com.songc.entity.meta.Equipment;
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
public class EquipmentDaoTest {
    private Long userId = 100L;

    @Autowired
    private EquipmentDao equipmentDao;

    @Test
    public void findByUserId() throws Exception {
        List<Equipment> equipmentList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Equipment equipment = new Equipment();
            equipment.setUserId(userId);
            equipment.setName("songc" + 1);
            equipmentList.add(equipment);
        }
        equipmentDao.save(equipmentList);
        List<Equipment> equipmentList1 = equipmentDao.findByUserId(userId);
        assertEquals(equipmentList.size(), equipmentList1.size());
        equipmentDao.delete(equipmentList1.get(0).getId());
        List<Equipment> equipmentList2 = equipmentDao.findByUserId(userId);
        assertEquals(equipmentList2.size(), equipmentList1.size() - 1);
    }

}