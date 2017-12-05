package com.songc.dao;

import com.songc.entity.meta.ImageMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/12/4
 */
public interface ImageMetaDao extends JpaRepository<ImageMeta, Long> {
    List<ImageMeta> findByUserId(Long userId);
}
