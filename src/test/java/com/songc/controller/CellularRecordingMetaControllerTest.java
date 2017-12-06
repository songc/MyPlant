package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.dao.CellularRecordingMetaDao;
import com.songc.entity.meta.CellularRecordingMeta;
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
public class CellularRecordingMetaControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CellularRecordingMetaDao recordingMetaDao;

    private CellularRecordingMeta cellularRecordingMeta = new CellularRecordingMeta();

    private String baseUrl = "/user/100/cellularRecording";

    private ObjectMapper objectMapper = new ObjectMapper();

    {
        cellularRecordingMeta.setName("songc");
        cellularRecordingMeta.setUserId(100L);
    }

    @Test
    public void save() throws Exception {
        given(this.recordingMetaDao.save(any(CellularRecordingMeta.class))).willReturn(cellularRecordingMeta);
        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cellularRecordingMeta)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(cellularRecordingMeta)));
    }

    @Test
    public void update() throws Exception {
        given(this.recordingMetaDao.save(any(CellularRecordingMeta.class))).willReturn(cellularRecordingMeta);
        mvc.perform(put(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cellularRecordingMeta)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(cellularRecordingMeta)));
    }

    @Test
    public void findByUserId() throws Exception {
        List<CellularRecordingMeta> recordingMetaList = new ArrayList<>();
        recordingMetaList.add(cellularRecordingMeta);
        given(this.recordingMetaDao.findByUserId(anyLong())).willReturn(recordingMetaList);
        mvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recordingMetaList)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(recordingMetaList)));
    }

    @Test
    public void findOne() throws Exception {
        Long id = 100L;
        given(this.recordingMetaDao.findOne(id)).willReturn(cellularRecordingMeta);
        mvc.perform(get(baseUrl + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(cellularRecordingMeta)));
    }

    @Test
    public void delete() throws Exception {
        Long id = 100L;
        mvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/" + id))
                .andExpect(status().isOk());
        verify(recordingMetaDao).delete(id);
    }

}