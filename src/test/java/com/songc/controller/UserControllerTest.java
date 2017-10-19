package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.dto.UserDTO;
import com.songc.entity.Dataset;
import com.songc.entity.User;
import com.songc.service.DatasetService;
import com.songc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private DatasetService datasetService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testAddUser() throws Exception {
        User user = new User();
        user.setUsername("songc");
        user.setPassword("455");
        user.setAddress("beijing");
        user.setEmail("44350255@qq.com");
        given(this.userService.save(any(User.class))).willReturn(user);
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(content().string(mapper.writeValueAsString(new UserDTO(user))));
    }

    @Test
    public void findUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User();
        user.setUsername("songc");
        user.setPassword("455");
        user.setAddress("beijing");
        user.setEmail("44350255@qq.com");
        given(this.userService.findUser(any(Long.TYPE))).willReturn(user);
        this.mockMvc.perform(get("/user/1"))
                .andExpect(content().string(mapper.writeValueAsString(new UserDTO(user))));
    }

    @Test
    public void login() throws Exception {
        User user = new User();
        user.setUsername("songc");
        user.setPassword("443502355");
        user.setAddress("beijing");
        user.setEmail("44350255@qq.com");
        given(this.userService.findUserByUsernameAndPassword("songc","443502355")).willReturn(user);
        this.mockMvc.perform(post("/user/login").param("username", "songc")
                .param("password", "443502355"))
                .andExpect(content().string(mapper.writeValueAsString(new UserDTO(user))));
    }

    @Test
    public void findDataset() throws Exception {
        Integer number = 1;
        Integer size = 10;
        Pageable pageable = new PageRequest(number, size);
        List<Dataset> datasets = new ArrayList<>();
        Page<Dataset> datasetPage = new PageImpl<>(datasets, pageable, 10);
        given(this.datasetService.getPageDatasetByUserId(anyLong(), anyInt(), anyInt())).willReturn(datasetPage);
        this.mockMvc.perform(get("/user/1/dataset").param("number", number.toString())
                .param("size", size.toString()))
                .andExpect(content().string(mapper.writeValueAsString(datasetPage)));
    }

    @Test
    public void save() throws Exception {
        Dataset dataset = new Dataset();
        dataset.setUserId(1L);
        given(this.datasetService.save(dataset)).willReturn(dataset);
        this.mockMvc.perform(post("/user/1/dataset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dataset)))
                .andExpect(content().string(mapper.writeValueAsString(dataset)));
    }
}
