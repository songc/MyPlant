package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.entity.Dataset;
import com.songc.service.DatasetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(DatasetController.class)
public class DatasetControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatasetService datasetService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addDatasetTest () throws Exception {
        Long id = (long)1;
        Dataset dataset = new Dataset();
        given(this.datasetService.save(any(Dataset.class))).willReturn(id);
        this.mockMvc.perform(post("/dataset").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dataset)))
                .andExpect(content().string(id.toString()));
    }

    @Test
    public void getPageDatasetTest() throws Exception {
        Integer page = 1;
        Integer size = 10;
        Pageable pageable = new PageRequest(page, size);
        List<Dataset> datasets = new ArrayList<>();
        Page<Dataset> datasetPage = new PageImpl<Dataset>(datasets, pageable,10);
        given(this.datasetService.getPageDataset(page, size)).willReturn(datasetPage);
        this.mockMvc.perform(get("/dataset").param("page",page.toString())
                .param("size", size.toString())).andExpect(content().string(mapper.writeValueAsString(datasetPage)));
    }
}
