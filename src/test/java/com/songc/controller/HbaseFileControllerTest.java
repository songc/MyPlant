package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.dto.HbaseFileWithContentDTO;
import com.songc.dto.HbaseFileWithStringContentDTO;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
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
        HbaseFile hbaseFile1 = new HbaseFile("123456", 100L, "23.csv", "wrefds".getBytes());
        HbaseFileWithContentDTO hbaseFile = new HbaseFileWithContentDTO(hbaseFile1);
        HbaseFileWithStringContentDTO hbase2 = new HbaseFileWithStringContentDTO(hbaseFile);
        given(hbaseService.findContent("123456")).willReturn(hbaseFile);
        mockMvc.perform(get("/hbase/123456"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(hbase2)));
    }

    @Test
    public void getPng() throws Exception {
        String path = "C:\\Users\\songc\\Desktop\\临时\\1.png";
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        Long length = file.length();
        byte[] fileContent = new byte[length.intValue()];
        in.read(fileContent);
        HbaseFile hbaseFile = new HbaseFile();
        hbaseFile.setName(file.getName());
        hbaseFile.setContent(fileContent);
        HbaseFileWithContentDTO hbaseFile1 = new HbaseFileWithContentDTO(hbaseFile);
        given(hbaseService.findContent(anyString())).willReturn(hbaseFile1);
        mockMvc.perform(get("/hbase/png/123456"))
                .andExpect(status().isOk());
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