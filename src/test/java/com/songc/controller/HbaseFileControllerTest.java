package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.dto.HbaseFileWithContentDTO;
import com.songc.entity.HbaseFile;
import com.songc.entity.data.StatusEnum;
import com.songc.service.HbaseService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HbaseFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HbaseService hbaseService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void find() throws Exception {
        HbaseFile hbaseFile = new HbaseFile("123456", 100L, "23.csv", "wrefds".getBytes());
        given(hbaseService.find("123456")).willReturn(hbaseFile);
        mockMvc.perform(get("/hbase/123456"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(new HbaseFileWithContentDTO(hbaseFile))));
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/hbase/123456"))
                .andExpect(status().isOk())
                .andExpect(content().string(StatusEnum.SUCCESS.toString()));
    }

    @Test
    public void deleteList() throws Exception {
        List<String> rowKeyList = new ArrayList<>();
        rowKeyList.add("00001245");
        rowKeyList.add("12346411");
        mockMvc.perform(MockMvcRequestBuilders.delete("/hbase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(rowKeyList)))
                .andExpect(status().isOk())
                .andExpect(content().string(StatusEnum.SUCCESS.toString()));
    }

}