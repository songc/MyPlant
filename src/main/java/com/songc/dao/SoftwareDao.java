package com.songc.dao;

import com.songc.entity.meta.Software;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
public interface SoftwareDao extends JpaRepository<Software, Long> {
    List<Software> findByUserId(Long userId);
}
