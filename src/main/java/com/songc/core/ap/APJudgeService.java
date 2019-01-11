package com.songc.core.ap;

import com.songc.core.ap.detect.wave.RawWave;
import com.songc.core.ap.detect.wave.Wave;
import com.songc.core.ap.judge.Judge;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songc
 * @date 3/1/2017
 */
public class APJudgeService {
    private Double[][] data;
    private int rate;
    private Judge judge = Judge.getInstance();

    public APJudgeService(Double[][] data, int rate) {
        this.data = data;
        this.rate = rate;
    }

    private List<Wave> init() {
        List<Wave> waveList = new ArrayList<>();
        for (Double[] doubles : data) {
            double[] temp = new double[doubles.length];
            int i = 0;
            for (Double aDouble : doubles) {
                temp[i++] = aDouble;
            }
            waveList.add(new RawWave(temp, rate));
        }
        return waveList;
    }

    /**
     * 判断信号是否为AP
     *
     * @return
     */
    public List<Boolean> isAP() {
        List<Wave> waveList = init();
        List<Boolean> result = new ArrayList<>();
        for (Wave wave : waveList) {
            result.add(judge.isAP(wave));
        }
        return result;
    }
}
