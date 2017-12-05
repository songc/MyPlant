package com.songc.dao;

import com.songc.entity.meta.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
public interface EquipmentDao extends JpaRepository<Equipment, Long> {
    List<Equipment> findByUserId(Long userId);
}
