package com.songc.dao;

import com.songc.entity.meta.CellularRecordingMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
public interface CellularRecordingMetaDao extends JpaRepository<CellularRecordingMeta, Long> {
    List<CellularRecordingMeta> findByUserId(Long userId);
}
