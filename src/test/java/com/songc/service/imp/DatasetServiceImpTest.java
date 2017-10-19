package com.songc.service.imp;

import com.songc.dao.DatasetDao;
import com.songc.entity.Dataset;
import com.songc.entity.HbaseFile;
import com.songc.service.DatasetService;
import com.songc.service.HbaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatasetServiceImpTest {

    @MockBean
    private DatasetDao datasetDao;

    @MockBean
    private HbaseService hbaseService;

    @Autowired
    private DatasetService datasetService;

    @Test
    public void save() throws Exception {
        Dataset dataset = new Dataset();
        dataset.setUserId(1L);

        given(datasetDao.save(dataset)).willReturn(dataset);
        Dataset dataset1 = datasetService.save(dataset);

        assertEquals(dataset.getUserId(), dataset1.getUserId());
    }

    @Test
    public void getPageDataset() throws Exception {
        List<Dataset> datasets = new ArrayList<>();
        Page<Dataset> datasetPage = new PageImpl<>(datasets);
        given(datasetDao.findAll(any(Pageable.class))).willReturn(datasetPage);
        Page<Dataset> datasetPage1 = datasetService.getPageDataset(1, 2);
        assertEquals(datasetPage.getTotalElements(), datasetPage1.getTotalElements());
    }

    public void getPageDatasetByUserId() {
        List<Dataset> datasets = new ArrayList<>();
        Page<Dataset> datasetPage = new PageImpl<>(datasets);
        given(datasetDao.findByUserIdIs(anyLong(), any(Pageable.class))).willReturn(datasetPage);
        Page<Dataset> datasetPage1 = datasetService.getPageDataset(1, 2);
        assertEquals(datasetPage.getTotalElements(), datasetPage1.getTotalElements());
    }

    @Test
    public void findOne() throws Exception {
        Long id = 100L;
        Dataset dataset = new Dataset();
        dataset.setId(id);
        given(datasetDao.findOne(id)).willReturn(dataset);
        Dataset dataset1 = datasetService.findOne(id);
        assertEquals(id, dataset1.getId());
    }

    @Test
    public void findByUserId() throws Exception {
        Long id = 100L;
        List<Dataset> datasets = new ArrayList<>();
        datasets.add(new Dataset());
        given(datasetDao.findByUserId(id)).willReturn(datasets);
        List<Dataset> datasets1 = datasetService.findByUserId(id);
        assertEquals(datasets.size(), datasets1.size());
    }

    @Test
    public void delete() throws Exception {
        Long id = 100L;
        datasetService.delete(id);
        verify(datasetDao).delete(id);
    }


    public void findFile() {
        Long id = 100L;
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        hbaseFiles.add(new HbaseFile());
        given(hbaseService.findByParentId(id)).willReturn(hbaseFiles);
        List<HbaseFile> hbaseFiles1 = datasetService.findFile(id);
        assertEquals(hbaseFiles.size(), hbaseFiles1.size());
    }

}