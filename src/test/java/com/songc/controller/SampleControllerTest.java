package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.dao.SampleDao;
import com.songc.entity.meta.Sample;
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
 * on 2017/12/6
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SampleDao sampleDao;

    private ObjectMapper objectMapper = new ObjectMapper();
    private String baseUrl = "/user/100/sample";
    private Sample sample = new Sample();

    private final long userId = 100L;

    {
        sample.setName("songc");
        sample.setUserId(userId);
    }

    @Test
    public void save() throws Exception {
        given(sampleDao.save(any(Sample.class))).willReturn(sample);
        mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(sample)));
    }

    @Test
    public void update() throws Exception {
        given(sampleDao.save(any(Sample.class))).willReturn(sample);
        mockMvc.perform(put(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(sample)));
    }

    @Test
    public void findByUserId() throws Exception {
        List<Sample> sampleList = new ArrayList<>();
        sampleList.add(sample);
        given(sampleDao.findByUserId(userId)).willReturn(sampleList);
        mockMvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleList)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(sampleList)));
    }

    @Test
    public void findOne() throws Exception {
        Long id = 100L;
        given(sampleDao.findOne(id)).willReturn(sample);
        mockMvc.perform(get(baseUrl + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(sample)));
    }

    @Test
    public void delete() throws Exception {
        Long id = 100L;
        mockMvc.perform((MockMvcRequestBuilders.delete(baseUrl + "/" + id)))
                .andExpect(status().isOk());
        verify(sampleDao).delete(100L);
    }

}