package com.songc.controller;

import com.songc.core.ap.APExtractService;
import com.songc.core.ap.APJudgeService;
import com.songc.core.ap.detect.wave.APInRawWave;
import com.songc.util.SmoothFilter;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author songc
 */
@RestController
@RequestMapping(value = "/analysis")
public class SignalAnalysisController {

    @PostMapping(value = "/smooth/{windowWidth}")
    public List<double[]> smooth(@RequestBody Double[][] rawSignal, @PathVariable("windowWidth") Integer windowWidth) {
        Assert.isTrue(windowWidth > 1, "windowWidth must > 1");
        return SmoothFilter.filter(Arrays.asList(rawSignal), windowWidth);
    }

    @PostMapping(value = "/ap/extraction/{rate}")
    public List<List<APInRawWave>> findAP(@RequestBody Double[][] rawSignal, @PathVariable Integer rate) {
        return new APExtractService(rawSignal, rate).getAllAPInfo();
    }

    @PostMapping(value = "/ap/judgement/{rate}")
    public List<Boolean> isAP(@RequestBody Double[][] apLike, @PathVariable Integer rate) {
        return new APJudgeService(apLike, rate).isAP();
    }
}
