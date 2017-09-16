package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.entity.Folder;
import com.songc.entity.HbaseFile;
import com.songc.entity.data.StatusEnum;
import com.songc.service.FolderService;
import com.songc.service.MultipartFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest(FolderController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FolderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FolderService folderService;

    @MockBean
    private MultipartFileService multipartFileService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void save() throws Exception {
        Folder folder = new Folder();
        given(folderService.save(any(String.class), any(Long.TYPE))).willReturn(folder);
        this.mockMvc.perform(post("/folder/1/subfolder")
                .param("name", "songc"))
                .andExpect(content().string(mapper.writeValueAsString(folder)));
    }

    @Test
    public void find() throws Exception {
        Folder folder = new Folder();
        given(folderService.find(any(Long.TYPE))).willReturn(folder);
        this.mockMvc.perform(get("/folder/1"))
                .andExpect(content().string(mapper.writeValueAsString(folder)));
    }

    @Test
    public void delete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/folder/1"))
                .andExpect(content().string(StatusEnum.SUCCESS.toString()));
    }

    @Test
    public void findSubfolder() throws Exception {
        List<Folder> folders = new ArrayList<>();
        given(folderService.findSubFolder(any(Long.TYPE))).willReturn(folders);
        this.mockMvc.perform(get("/folder/1/subfolder"))
                .andExpect(content().string(mapper.writeValueAsString(folders)));
    }

    @Test
    public void saveFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "content".getBytes());
        HbaseFile hbaseFile = new HbaseFile();
        given(this.multipartFileService.save(any(Long.TYPE), any(MultipartFile.class))).willReturn(hbaseFile);
        this.mockMvc.perform(fileUpload("/folder/1/singlefile").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(hbaseFile)));
    }

    @Test
    public void saveFiles() throws Exception {
        List<MultipartFile> files = new ArrayList<>();
        MockMultipartFile file = new MockMultipartFile("songc", "content".getBytes());
        files.add(file);
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        hbaseFiles.add(new HbaseFile());
        given(multipartFileService.save(any(Long.TYPE), any(List.class))).willReturn(hbaseFiles);
        mockMvc.perform(fileUpload("/folder/1/mulfile").file(file).file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(hbaseFiles)));
    }

    @Test
    public void findSubFile() throws Exception {
        List<HbaseFile> files = new ArrayList<>();
        given(folderService.findSubFile(any(Long.TYPE))).willReturn(files);
        this.mockMvc.perform(get("/folder/1/subfile"))
                .andExpect(content().string(mapper.writeValueAsString(files)));
    }

}
