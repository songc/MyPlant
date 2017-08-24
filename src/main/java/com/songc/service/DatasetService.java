package com.songc.service;

import com.songc.entity.Dataset;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by songc on 4/28/2017.
 */
public interface DatasetService {

    Long save(Dataset dataset);

    Page<Dataset> getPageDataset(Integer page, Integer size);
}
