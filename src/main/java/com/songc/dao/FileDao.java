package com.songc.dao;

import com.songc.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by songc on 4/27/2017.
 */
public interface FileDao extends JpaRepository<File, Long> {
    List<File> findAllByParentId(Long parentId);
}
