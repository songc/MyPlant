package com.songc.service.imp;

import com.songc.dao.DatasetDao;
import com.songc.entity.Dataset;
import com.songc.entity.Folder;
import com.songc.service.DatasetService;
import com.songc.service.FolderService;
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
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatasetServiceImpTest {

    @MockBean
    private DatasetDao datasetDao;

    @MockBean
    private FolderService folderService;

    @Autowired
    private DatasetService datasetService;

    @Test
    public void save() throws Exception {
        Dataset dataset = new Dataset();
        dataset.setUserId(1L);
        Folder folder = mock(Folder.class);
        given(folderService.save(any(Folder.class))).willReturn(folder);
        when(folder.getFolderId()).thenReturn(2L);

        given(datasetDao.save(dataset)).willReturn(dataset);
        Dataset dataset1 = datasetService.save(dataset);

        verify(folderService).save(any(Folder.class));
        verify(folder).getFolderId();
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

    @Test
    public void findOne() throws Exception {
        Long id = 100L;
        Dataset dataset = new Dataset();
        dataset.setDatasetId(id);
        given(datasetDao.findOne(id)).willReturn(dataset);
        Dataset dataset1 = datasetService.findOne(id);
        assertEquals(id, dataset1.getDatasetId());
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

}