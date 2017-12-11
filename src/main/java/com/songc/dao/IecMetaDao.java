package com.songc.dao;

import com.songc.entity.meta.IecMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
public interface IecMetaDao extends JpaRepository<IecMeta, Long> {
    List<IecMeta> findByUserId(Long userId);
}
