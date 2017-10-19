package com.songc.dao;

import com.songc.entity.Dataset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by songc on 4/27/2017.
 */
public interface DatasetDao extends JpaRepository<Dataset, Long> {
    List<Dataset> findByUserId(Long userId);

    Page<Dataset> findByUserIdIs(Long userId, Pageable pageable);
}
