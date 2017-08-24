package com.songc.dao;

import com.songc.entity.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by songc on 4/27/2017.
 */
public interface DatasetDao extends JpaRepository<Dataset, Long> {
}
