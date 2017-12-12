package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.dao.DatasetMetaDao;
import com.songc.entity.meta.DatasetMeta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created By @author songc
 * on 2017/12/5
 */
@RunWith(SpringRunner.class)
//@WebMvcTest(DatasetMetaController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DatasetMetaControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DatasetMetaDao datasetMetaDao;

    private DatasetMeta datasetMeta = new DatasetMeta();

    private String baseUrl = "/user/100/datasetMeta";
    private Long userId = 100L;

    private ObjectMapper objectMapper = new ObjectMapper();

    {
        datasetMeta.setName("songc");
        datasetMeta.setUserId(userId);
    }

    @Test
    public void save() throws Exception {
        given(this.datasetMetaDao.save(any(DatasetMeta.class))).willReturn(datasetMeta);
        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datasetMeta)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(datasetMeta)));
    }

    @Test
    public void update() throws Exception {
        given(this.datasetMetaDao.save(any(DatasetMeta.class))).willReturn(datasetMeta);
        mvc.perform(put(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datasetMeta)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(datasetMeta)));
    }

    @Test
    public void findByUserId() throws Exception {
        List<DatasetMeta> datasetMetaList = new ArrayList<>();
        datasetMetaList.add(datasetMeta);
        given(this.datasetMetaDao.findByUserId(userId)).willReturn(datasetMetaList);
        mvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(datasetMetaList)));
    }

    @Test
    public void findOne() throws Exception {
        Long id = 100L;
        given(this.datasetMetaDao.findOne(id)).willReturn(datasetMeta);
        mvc.perform(get(baseUrl + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(datasetMeta)));
    }

    @Test
    public void delete() throws Exception {
        Long id = 100L;
        mvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/" + id))
                .andExpect(status().isOk());
        verify(datasetMetaDao).delete(id);
    }

}