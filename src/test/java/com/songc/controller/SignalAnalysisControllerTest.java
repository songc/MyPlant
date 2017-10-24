package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void smooth() throws Exception {
        List<double[]> rawSignals = new ArrayList<>();
        double[] signal1 = {1, 2, 3, 4, 5, 6, 7};
        double[] signal2 = new double[10];
        Arrays.fill(signal2, 20);
        rawSignals.add(signal2);
        this.mockMvc.perform(post("/analysis/smooth/2").content(mapper.writeValueAsString(rawSignals)))
                .andExpect(content().string(mapper.writeValueAsString(rawSignals)));
        rawSignals.add(signal1);
        String json = mapper.writeValueAsString(rawSignals);
        Double[][] er = mapper.readValue(json, Double[][].class);
        System.out.println(json);
        System.out.println(er[1][1]);
        String responseString = this.mockMvc.perform(post("/analysis/smooth/2").content(mapper.writeValueAsString(rawSignals)))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }

}