package com.songc.controller;

import com.songc.core.ap.APExtractService;
import com.songc.core.ap.APJudgeService;
import com.songc.core.ap.detect.wave.APInRawWave;
import com.songc.service.ImageAnalysisService;
import com.songc.util.SmoothFilter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ImageAnalysisService imageAnalysisService;

    @Autowired
    public SignalAnalysisController(ImageAnalysisService imageAnalysisService) {
        this.imageAnalysisService = imageAnalysisService;
    }

    /**
     * 平滑滤波
     *
     * @param rawSignal   需要滤波的一维信号
     * @param windowWidth 平滑滤波的窗口大小
     * @return 滤波后的一维信号
     */
    @ApiOperation(value = "Smooth waveform")
    @PostMapping(value = "/smooth/{windowWidth}")
    public List<double[]> smooth(@RequestBody Double[][] rawSignal, @PathVariable("windowWidth") Integer windowWidth) {
        Assert.isTrue(windowWidth > 1, "windowWidth must > 1");
        return SmoothFilter.filter(Arrays.asList(rawSignal), windowWidth);
    }

    /**
     * 提取波形中的类AP波形信息
     * @param rawSignal 需要提取的一维信号列表。（二维数组）
     * @param rate 信号的频率，默认是10（HZ）
     * @return 所有波形中提取的类AP波形信息
     */
    @ApiOperation(value = "extract AP-like waveform from the raw waveform")
    @PostMapping(value = "/ap/extraction/{rate}")
    public List<List<APInRawWave>> findAP(@RequestBody Double[][] rawSignal, @PathVariable Integer rate) {
        return new APExtractService(rawSignal, rate).getAllAPInfo();
    }

    /**
     * 判断类AP波形信息是否为AP。
     * @param apLike 需要判断的AP 一维信号列表 （二维数组）
     * @param rate 信号的频率，默认是10（HZ）
     * @return 判断结果列表
     */
    @ApiOperation(value = "Determine if a waveform is an AP waveform")
    @PostMapping(value = "/ap/judgement/{rate}")
    public List<Boolean> isAP(@RequestBody Double[][] apLike, @PathVariable Integer rate) {
        return new APJudgeService(apLike, rate).isAP();
    }

    /**
     * 单区域荧光图像的电信号提取
     * @param datasetId 图像序列的数据集的id
     * @param startX 区域起点的X坐标
     * @param startY 区域起点的Y坐标
     * @param width 区域的宽度
     * @param height 区域的高度
     * @return 提取的信号（字符串数值）
     */
    @ApiOperation(value = "Get single region signal from images series")
    @GetMapping(value = "/image/single/{datasetId}")
    public String singleRegion(@PathVariable Long datasetId,
                               @RequestParam int startX,
                               @RequestParam int startY,
                               @RequestParam int width,
                               @RequestParam int height) {
        return imageAnalysisService.singleRegion(datasetId, startX, startY, width, height);
    }

    /**
     * 多区域荧光图像的电信号提取
     * @param datasetId 图像序列的数据集的id
     * @param width 区域宽度
     * @param height 区域高度
     * @return 提取的信号列表转换后的字符串。
     */
    @ApiOperation(value = "Get multiple region signal from images series")
    @GetMapping(value = "/image/multiple/{datasetId}")
    public String multiRegion(@PathVariable Long datasetId, @RequestParam int width, @RequestParam int height) {
        return imageAnalysisService.multiRegion(datasetId, width, height);
    }
}
