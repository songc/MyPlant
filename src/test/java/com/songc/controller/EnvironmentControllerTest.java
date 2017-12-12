package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.dao.EnvironmentDao;
import com.songc.entity.meta.Environment;
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
//@WebMvcTest(EnvironmentController.class)
public class EnvironmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvironmentDao dao;

    private ObjectMapper objectMapper = new ObjectMapper();
    private String baseUrl = "/user/100/environment";
    private Environment environment = new Environment();
    private Long userId = 100L;

    {
        environment.setUserId(userId);
        environment.setName("songc");
    }

    @Test
    public void save() throws Exception {
        given(dao.save(any(Environment.class))).willReturn(environment);
        mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(environment)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(environment)));
    }

    @Test
    public void update() throws Exception {
        given(dao.save(any(Environment.class))).willReturn(environment);
        mockMvc.perform(put(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(environment)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(environment)));
    }

    @Test
    public void findByUserId() throws Exception {
        List<Environment> environmentList = new ArrayList<>();
        environmentList.add(environment);
        given(dao.findByUserId(userId)).willReturn(environmentList);
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(environmentList)));
    }

    @Test
    public void findOne() throws Exception {
        Long id = 10L;
        given(dao.findOne(id)).willReturn(environment);
        mockMvc.perform(get(baseUrl + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(environment)));
    }

    @Test
    public void delete() throws Exception {
        Long id = 10L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/" + id))
                .andExpect(status().isOk());
        verify(dao).delete(id);
    }

}