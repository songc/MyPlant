package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.util.SmoothFilter;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author songc
 */
@RestController
@RequestMapping(value = "/analysis")
public class SignalAnalysisController {

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/smooth/{pointNum}")
    public List<double[]> smooth(@RequestBody String string, @PathVariable("pointNum") Integer pointNum) throws IOException {
        Assert.isTrue(pointNum > 1, "pointNum must > 1");
        Double[][] rawSignal = mapper.readValue(string, Double[][].class);
        return SmoothFilter.filter(Arrays.asList(rawSignal), pointNum);
    }
}
