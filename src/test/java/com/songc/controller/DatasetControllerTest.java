package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.entity.Dataset;
import com.songc.entity.data.StatusEnum;
import com.songc.service.DatasetService;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DatasetControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatasetService datasetService;

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
        this.mockMvc.perform(get("/dataset").param("page",page.toString())
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
}
