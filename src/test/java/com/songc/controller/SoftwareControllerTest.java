package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.dao.SoftwareDao;
import com.songc.entity.meta.Software;
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
//@WebMvcTest(SoftwareController.class)
public class SoftwareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SoftwareDao softwareDao;

    private ObjectMapper objectMapper = new ObjectMapper();
    private String baseUrl = "/user/100/software";
    private Software software = new Software();

    private final Long userId = 100L;

    {
        software.setName("songc");
        software.setUserId(userId);
    }

    @Test
    public void save() throws Exception {
        given(softwareDao.save(any(Software.class))).willReturn(software);
        mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(software)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(software)));
    }

    @Test
    public void update() throws Exception {
        given(softwareDao.save(any(Software.class))).willReturn(software);
        mockMvc.perform(put(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(software)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(software)));
    }

    @Test
    public void findByUserId() throws Exception {
        List<Software> softwareList = new ArrayList<>();
        softwareList.add(software);
        given(softwareDao.findByUserId(userId)).willReturn(softwareList);
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(softwareList)));
    }

    @Test
    public void findOne() throws Exception {
        Long id = 10L;
        given(softwareDao.findOne(id)).willReturn(software);
        mockMvc.perform(get(baseUrl + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(software)));
    }

    @Test
    public void delete() throws Exception {
        Long id = 10L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/" + id))
                .andExpect(status().isOk());
        verify(softwareDao).delete(id);
    }

}