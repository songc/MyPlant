package com.songc.dao;

import com.songc.entity.HadoopFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by songc on 4/27/2017.
 */
public interface HadoopFileDao extends JpaRepository<HadoopFile,Long> {
}
