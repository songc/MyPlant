package com.songc.dao;

import com.songc.entity.meta.DatasetMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
public interface DatasetMetaDao extends JpaRepository<DatasetMeta, Long> {
    List<DatasetMeta> findByUserId(Long userId);
}
