package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.dao.EquipmentDao;
import com.songc.entity.meta.Equipment;
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
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(EquipmentController.class)
public class EquipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentDao equipmentDao;

    private ObjectMapper objectMapper = new ObjectMapper();
    private String baseUrl = "/user/100/equipment";
    private Equipment equipment = new Equipment();

    private Long userId = 100L;

    {
        equipment.setUserId(userId);
        equipment.setName("songc");
    }

    @Test
    public void save() throws Exception {
        given(equipmentDao.save(any(Equipment.class))).willReturn(equipment);
        mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equipment)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(equipment)));
    }

    @Test
    public void update() throws Exception {
        given(equipmentDao.save(any(Equipment.class))).willReturn(equipment);
        mockMvc.perform(put(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equipment)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(equipment)));
    }

    @Test
    public void findByUserId() throws Exception {
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(equipment);
        given(equipmentDao.findByUserId(userId)).willReturn(equipmentList);
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(equipmentList)));
    }

    @Test
    public void findOne() throws Exception {
        Long id = 100L;
        given(equipmentDao.findOne(id)).willReturn(equipment);
        mockMvc.perform(get(baseUrl + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(equipment)));
    }

    @Test
    public void delete() throws Exception {
        Long id = 100L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/" + id))
                .andExpect(status().isOk());
        verify(equipmentDao).delete(id);
    }

}