package com.songc.service;

import com.songc.dto.HbaseFileDTO;
import com.songc.dto.HbaseFileWithContentDTO;
import com.songc.entity.Dataset;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by songc on 4/28/2017.
 */
public interface DatasetService {

    Dataset save(Dataset dataset);

    Page<Dataset> getPageDataset(Integer pageNumber, Integer pageSize);

    Page<Dataset> getPageDatasetByUserId(Long userId, Integer pageNumber, Integer pageSize);

    Page<Dataset> search(String keyWord, Integer pageNumber, Integer pageSize);

    Dataset findOne(Long id);

    List<Dataset> findByUserId(Long userId);

    List<HbaseFileWithContentDTO> findWithContentFile(Long parentId);

    List<HbaseFileDTO> findFile(Long parentId);

    void delete(Long id);

    Dataset update(Dataset dataset);

}
