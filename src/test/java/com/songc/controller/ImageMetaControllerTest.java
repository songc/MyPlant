package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.dao.ImageMetaDao;
import com.songc.entity.meta.ImageMeta;
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
 * on 2017/12/6
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(ImageMetaController.class)
public class ImageMetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageMetaDao imageMetaDao;

    private ObjectMapper objectMapper = new ObjectMapper();
    private String baseUrl = "/user/100/imageMeta";
    private ImageMeta imageMeta = new ImageMeta();

    {
        imageMeta.setName("songc");
        imageMeta.setUserId(100L);
    }

    @Test
    public void save() throws Exception {
        given(imageMetaDao.save(any(ImageMeta.class))).willReturn(imageMeta);
        mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(imageMeta)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(imageMeta)));
    }

    @Test
    public void update() throws Exception {
        given(imageMetaDao.save(any(ImageMeta.class))).willReturn(imageMeta);
        mockMvc.perform(put(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(imageMeta)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(imageMeta)));
    }

    @Test
    public void findByUserId() throws Exception {
        List<ImageMeta> imageMetaList = new ArrayList<>();
        imageMetaList.add(imageMeta);
        given(imageMetaDao.findByUserId(anyLong())).willReturn(imageMetaList);
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(imageMetaList)));
    }

    @Test
    public void findOne() throws Exception {
        Long id = 100L;
        given(imageMetaDao.findOne(id)).willReturn(imageMeta);
        mockMvc.perform(get(baseUrl + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(imageMeta)));
    }

    @Test
    public void delete() throws Exception {
        Long id = 100L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/" + id))
                .andExpect(status().isOk());
        verify(imageMetaDao).delete(id);
    }

}