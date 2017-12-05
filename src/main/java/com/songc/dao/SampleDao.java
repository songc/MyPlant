package com.songc.dao;

import com.songc.entity.meta.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
public interface SampleDao extends JpaRepository<Sample, Long> {
    List<Sample> findByUserId(Long userId);
}
