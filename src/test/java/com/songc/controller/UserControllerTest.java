package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.entity.User;
import com.songc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

//    @Test
//    public void testAddUser() throws Exception {
//        given(this.userService.save(any(User.class))).willReturn(new User(1));
//        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content("{\n" +
//                "\t\"email\":\"443502355@qq.com\",\n" +
//                "\t\"address\":\"beijing\"\n" +
//                "}")).andExpect(content().string("1"));
//    }

    @Test
    public void testGetUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User();
        given(this.userService.findUser(any(Long.TYPE))).willReturn(user);
        this.mockMvc.perform(get("/user/1")).andExpect(content().string(mapper.writeValueAsString(user)));
    }

    @Test
    public void testLogin() throws Exception {
        User user = mock(User.class);
        given(this.userService.findUserByUsernameAndPassword("songc","443502355")).willReturn(user);
        when(user.getUserId()).thenReturn(new Long(1));
        this.mockMvc.perform(post("/user/login").param("username", "songc")
                .param("password", "443502355")).andExpect(content().string("1"));
    }
}
