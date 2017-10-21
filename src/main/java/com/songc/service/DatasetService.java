package com.songc.service;

import com.songc.entity.Dataset;
import com.songc.entity.HbaseFile;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by songc on 4/28/2017.
 */
public interface DatasetService {

    Dataset save(Dataset dataset);

    Page<Dataset> getPageDataset(Integer pageNumber, Integer pageSize);

    Page<Dataset> getPageDatasetByUserId(Long userId, Integer pageNumber, Integer pageSize);

    Dataset findOne(Long id);

    List<Dataset> findByUserId(Long userId);

    List<HbaseFile> findFile(Long parentId);

    void delete(Long id);

    Dataset update(Dataset dataset);

}
