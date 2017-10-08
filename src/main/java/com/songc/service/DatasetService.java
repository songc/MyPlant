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

    Page<Dataset> getPageDataset(Integer page, Integer size);

    Dataset findOne(Long id);

    List<Dataset> findByUserId(Long userId);

    void delete(Long id);

    List<HbaseFile> findFile(Long parentId);

}
