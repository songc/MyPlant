package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.entity.Dataset;
import com.songc.entity.HbaseFile;
import com.songc.entity.data.StatusEnum;
import com.songc.service.DatasetService;
import com.songc.service.MultipartFileService;
import com.songc.util.MapperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DatasetControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatasetService datasetService;

    @MockBean
    private MultipartFileService multipartFileService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void save() throws Exception {
        Dataset dataset = new Dataset();
        given(this.datasetService.save(any(Dataset.class))).willReturn(dataset);
        this.mockMvc.perform(post("/dataset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dataset)))
                .andExpect(content().string(mapper.writeValueAsString(dataset)));
    }

    @Test
    public void getPageDatasetTest() throws Exception {
        Integer page = 1;
        Integer size = 10;
        Pageable pageable = new PageRequest(page, size);
        List<Dataset> datasets = new ArrayList<>();
        Page<Dataset> datasetPage = new PageImpl<>(datasets, pageable, 10);
        given(this.datasetService.getPageDataset(page, size)).willReturn(datasetPage);
        this.mockMvc.perform(get("/dataset").param("number", page.toString())
                .param("size", size.toString())).andExpect(content().string(mapper.writeValueAsString(datasetPage)));
    }

    @Test
    public void findById() throws Exception {
        Dataset dataset = new Dataset();
        dataset.setUserId(1L);
        given(this.datasetService.findOne(any(Long.TYPE))).willReturn(dataset);
        this.mockMvc.perform(get("/dataset/1"))
                .andExpect(content().string(mapper.writeValueAsString(dataset)));
    }

    @Test
    public void delete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/dataset/1"))
                .andExpect(content().string(StatusEnum.SUCCESS.toString()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void save1() throws Exception {
        MockMultipartFile file = new MockMultipartFile("songc", "content".getBytes());
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        hbaseFiles.add(new HbaseFile());
        given(multipartFileService.save(any(Long.TYPE), anyList())).willReturn(hbaseFiles);
        mockMvc.perform(fileUpload("/dataset/1/file").file(file).file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(MapperUtil.convert(hbaseFiles))));
    }

    @Test
    public void findFile() throws Exception {
        List<HbaseFile> files = new ArrayList<>();
        given(datasetService.findFile(any(Long.TYPE))).willReturn(files);
        this.mockMvc.perform(get("/dataset/1/file"))
                .andExpect(content().string(mapper.writeValueAsString(MapperUtil.convert(files))));
    }
}
