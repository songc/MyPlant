package com.songc.controller;

import com.songc.entity.File;
import com.songc.service.FileService;
import com.songc.service.MultipartFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @MockBean
    private MultipartFileService multipartFileService;

    @Test
    public void save() throws Exception {
        String msg = "Success";
        given(this.multipartFileService.save(any(MultipartFile.class))).willReturn(msg);
        this.mockMvc.perform(post("/file/single").param("file", ""));
    }

    @Test
    public void save1() throws Exception {
    }

    @Test
    public void findByParentId() throws Exception {
    }

}