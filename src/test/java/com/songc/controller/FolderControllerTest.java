package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.entity.File;
import com.songc.entity.Folder;
import com.songc.service.FolderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(FolderController.class)
public class FolderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FolderService folderService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void CreateSubfolderTest() throws Exception {
        given(folderService.createSubfolder("songc", (long) 1)).willReturn((long) 100);
        this.mockMvc.perform(post("/folder").param("name","songc")
                .param("parentId", "1")).andExpect(content().string("100"));
    }

    @Test
    public void findFolderTest() throws Exception {
        Long id = (long)1;
        Folder folder = new Folder();
        given(folderService.find(id)).willReturn(folder);
        this.mockMvc.perform(get("/folder").param("id", id.toString()))
                .andExpect(content().string(mapper.writeValueAsString(folder)));
    }

    @Test
    public void findAllSubfolderTest() throws Exception {
        Long parentId = (long)1;
        List<Folder> folders = new ArrayList<>();
        given(folderService.findAllSubfolder(parentId)).willReturn(folders);
        this.mockMvc.perform(get("/folder/subfolder").param("parentId", parentId.toString()))
                .andExpect(content().string(mapper.writeValueAsString(folders)));
    }

    @Test
    public void findAllSubFileTest() throws Exception {
        Long parentId = (long)1;
        List<File> files = new ArrayList<>();
        given(folderService.findAllSubFile(parentId)).willReturn(files);
        this.mockMvc.perform(get("/folder/subfile").param("parentId", parentId.toString()))
                .andExpect(content().string(mapper.writeValueAsString(files)));
    }

}
