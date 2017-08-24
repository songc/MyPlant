package com.songc.dao;

import com.songc.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by songc on 4/27/2017.
 */
public interface IECFileDao extends JpaRepository<File, Long> {
}
