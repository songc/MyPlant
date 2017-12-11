package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.dao.IecMetaDao;
import com.songc.entity.meta.IecMeta;
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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created By @author songc
 * on 2017/12/5
 */
@RunWith(SpringRunner.class)
//@WebMvcTest(CellularRecordingMetaController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IecMetaControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private IecMetaDao iecMetaDao;

    private IecMeta iecMeta = new IecMeta();

    private String baseUrl = "/user/100/iecMeta";

    private ObjectMapper objectMapper = new ObjectMapper();

    {
        iecMeta.setName("songc");
        iecMeta.setUserId(100L);
    }

    @Test
    public void save() throws Exception {
        given(this.iecMetaDao.save(any(IecMeta.class))).willReturn(iecMeta);
        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(iecMeta)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(iecMeta)));
    }

    @Test
    public void update() throws Exception {
        given(this.iecMetaDao.save(any(IecMeta.class))).willReturn(iecMeta);
        mvc.perform(put(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(iecMeta)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(iecMeta)));
    }

    @Test
    public void findByUserId() throws Exception {
        List<IecMeta> recordingMetaList = new ArrayList<>();
        recordingMetaList.add(iecMeta);
        given(this.iecMetaDao.findByUserId(anyLong())).willReturn(recordingMetaList);
        mvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recordingMetaList)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(recordingMetaList)));
    }

    @Test
    public void findOne() throws Exception {
        Long id = 100L;
        given(this.iecMetaDao.findOne(id)).willReturn(iecMeta);
        mvc.perform(get(baseUrl + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(iecMeta)));
    }

    @Test
    public void delete() throws Exception {
        Long id = 100L;
        mvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/" + id))
                .andExpect(status().isOk());
        verify(iecMetaDao).delete(id);
    }

}