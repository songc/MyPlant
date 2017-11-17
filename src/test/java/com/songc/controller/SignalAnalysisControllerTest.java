package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.service.ImageAnalysisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignalAnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageAnalysisService imageAnalysisService;

    private ObjectMapper mapper = new ObjectMapper();

    private Double[][] data = {{-28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -28.09, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -28.09, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -28.09, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.09, -28.7, -28.7, -28.09, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -26.87, -27.48, -27.48, -27.48, -27.48, -26.87, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -27.48, -43.97, -29.92, -27.48, -26.87, -25.65, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -25.65, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -26.26, -25.65, -26.26, -25.65, -26.26, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.26, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -27.48, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -27.48, -26.87, -27.48, -26.87, -27.48, -26.87, -27.48, -27.48, -27.48, -27.48, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.26, -26.87, -26.26, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.87, -26.26, -26.26, -26.26, -26.26, -26.26, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -26.87, -26.87, -26.87, -26.87, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -28.09, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -29.31, -29.31, -29.31, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -29.31, -29.31, -29.31, -29.31, -28.7, -28.7, -28.7, -29.31, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.09, -28.7, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -28.09, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -28.09, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -25.65, -25.65, -25.65, -26.26, -26.26, -25.65, -25.65, -26.26, -26.26, -26.26, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -25.65, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.87, -26.87, -26.26, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -27.48, -26.87, -26.87, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -27.48, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -38.47, -28.7, -26.26, -25.65, -25.04, -26.87, -26.87, -26.87, -26.87, -27.48, -27.48, -27.48, -28.09, -28.09, -28.09, -28.7, -28.7, -29.31, -29.31, -29.31, -29.92, -29.92, -29.92, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -29.31, -29.31, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.7, -28.7, -28.09, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -26.87, -27.48, -26.87, -26.87, -27.48, -27.48, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -27.48, -26.87, -26.87, -26.87, -27.48, -27.48, -26.87, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -26.87, -27.48, -27.48, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -27.48, -26.87, -26.87, -26.87, -26.87, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -26.87, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -26.87, -26.87, -27.48, -26.87, -27.48, -26.87, -27.48, -27.48, -27.48, -26.87, -27.48, -26.87, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.7, -28.09, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.09, -28.09, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -27.48, -27.48, -27.48, -27.48, -28.09, -28.09, -28.09, -28.09, -27.48, -27.48, -28.09, -28.09, -28.09, -28.09, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.7, -28.7, -28.09, -28.09, -28.09, -28.7, -28.09, -28.09, -28.09, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -29.31, -27.48, -25.65, -25.04, -25.04, -25.65, -26.26, -26.26, -26.26, -26.26, -26.87, -26.87, -26.87, -27.48, -27.48, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -28.7, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -26.87, -25.65, -25.04, -24.43, -23.82, -23.82, -25.04, -25.65, -25.65, -26.26, -26.26, -26.26, -26.87, -26.87, -26.87, -27.48, -27.48, -28.09, -28.09, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.09, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -26.87, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.26, -26.26, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.26, -26.87, -26.87, -26.87, -26.87, -26.26, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.26, -26.87, -26.87, -26.87, -26.87, -26.87, -26.26, -26.26, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.26, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -27.48, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -25.65, -20.15, -18.32, -17.71, -18.32, -23.82, -25.65, -26.26, -26.87, -27.48, -27.48, -28.09, -28.7, -28.7, -29.31, -29.31, -29.31, -29.92, -29.92, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -29.31, -28.7, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -28.09, -28.09, -28.09, -28.09, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -21.98, -19.54, -18.32, -19.54, -24.43, -25.65, -26.26, -26.87, -27.48, -27.48, -27.48, -28.09, -28.09, -28.7, -28.7, -28.7, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.31, -29.31, -29.31, -29.31, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.31, -29.92, -29.92, -29.92, -29.92, -29.92, -29.31, -29.92, -29.31, -29.31, -29.92, -29.92, -29.92, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -28.7, -28.7, -28.7, -29.31, -28.7, -28.7, -29.31, -29.31, -28.7, -29.31, -28.7, -28.7, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -29.31, -28.7, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -26.26, -22.59, -21.37, -20.76, -20.15, -20.15, -20.76, -21.37, -25.04, -27.48, -28.09, -28.7, -28.7, -29.31, -29.31, -29.31, -29.92, -29.92, -30.53, -30.53, -30.53, -31.14, -31.14, -31.14, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -30.53, -31.14, -31.14, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -29.92, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -29.92, -30.53, -30.53, -29.92, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -30.53, -29.92, -29.92, -29.92, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -29.31, -29.92, -29.92, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -28.7, -28.7, -28.7, -27.48, -28.7, -28.7, -28.7, -28.7, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.09, -28.09, -28.09, -28.7, -28.09, -28.09, -28.7, -28.09, -28.7, -28.09, -28.09, -28.09, -28.09, -28.7, -28.09, -28.7, -28.7, -28.09, -28.09, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.7, -28.09, -28.7, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -28.09, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -29.31, -28.7, -28.7, -28.7, -28.7, -28.7, -29.31, -29.31, -29.92, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.92, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -28.7, -28.7, -28.7, -28.7, -28.7, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -28.7, -29.31, -28.7, -28.7, -28.7, -29.31, -21.98, -18.93, -18.32, -18.32, -25.65, -28.09, -28.7, -28.7, -29.31, -29.92, -29.31, -29.92, -29.92, -30.53, -30.53, -30.53, -31.14, -31.14, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -32.36, -31.75, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -32.36, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.75, -31.14, -31.75, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -30.53, -30.53, -30.53, -30.53, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -29.92, -30.53, -30.53, -30.53, -30.53, -29.92, -30.53, -30.53, -30.53, -30.53, -29.92, -30.53, -29.92, -30.53, -30.53, -29.92, -29.92, -30.53, -30.53, -29.92, -30.53, -29.92, -29.92, -29.92, -29.92, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -29.92, -30.53, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.31, -29.92, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.92, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.31, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -30.53, -29.92, -29.92, -29.92, -30.53, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -31.14, -30.53, -31.14, -30.53, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14, -31.14}};

    @Test
    public void smooth() throws Exception {
        List<double[]> rawSignals = new ArrayList<>();
        double[] signal1 = {1, 2, 3, 4, 5, 6, 7};
        double[] signal2 = new double[10];
        Arrays.fill(signal2, 20);
        rawSignals.add(signal2);
        this.mockMvc.perform(post("/analysis/smooth/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(rawSignals)))
                .andExpect(content().string(mapper.writeValueAsString(rawSignals)));
        rawSignals.add(signal1);
        String json = mapper.writeValueAsString(rawSignals);
        Double[][] er = mapper.readValue(json, Double[][].class);
        System.out.println(json);
        System.out.println(er[1][1]);
        String responseString = this.mockMvc.perform(post("/analysis/smooth/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(rawSignals)))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }

    @Test
    public void findAP() throws Exception {
        this.mockMvc.perform(post("/analysis/ap/extraction/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(data)))
                .andExpect(status().isOk());
    }

    @Test
    public void isAP() throws Exception {
        this.mockMvc.perform(post("/analysis/ap/judgement/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(data)))
                .andExpect(status().isOk())
                .andExpect(content().string("[false]"));
    }

    @Test
    public void singleRegion() throws Exception {
        double[] result = {1, 2, 3, 4, 5};
        given(this.imageAnalysisService.singleRegion(100L, 50, 50, 50, 50))
                .willReturn(result);
        this.mockMvc.perform(get("/analysis/image/single/100")
                .param("startX", "50")
                .param("startY", "50").param("width", "50")
                .param("height", "50"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(result)));
    }

    @Test
    public void multiRegion() throws Exception {
        List<double[]> result = new ArrayList<>();
        result.add(new double[]{1, 2, 4, 5, 6});
        given(this.imageAnalysisService.multiRegion(100L, 50, 50)).willReturn(result);
        this.mockMvc.perform(get("/analysis/image/multiple/100")
                .param("width", "50")
                .param("height", "50"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(result)));
    }
}