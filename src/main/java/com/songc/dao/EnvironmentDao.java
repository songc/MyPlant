package com.songc.dao;

import com.songc.entity.meta.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/5
 */
public interface EnvironmentDao extends JpaRepository<Environment, Long> {
    List<Environment> findByUserId(Long userId);
}
