package com.songc.core.ap.util;

import com.songc.core.ap.detect.wave.RawWave;
import com.songc.core.ap.detect.wave.Wave;

/**
 * 转换波的频率
 * Created by songc on 3/1/2017.
 */
public class RateChange {
    private int targetRate; //目标频率

    public RateChange(int targetRate) {
        this.targetRate = targetRate;
    }

    /**
     * 将波形转换为目标频率的波形
     *
     * @param wave
     * @return
     */
    public Wave execuse(Wave wave) {
        if (wave.getRate() > targetRate) {
            return minusRate(wave);
        } else if (wave.getRate() == targetRate) {
            return wave;
        } else {
            return addRate(wave);
        }
    }

    /**
     * todo 待会做
     * 增加频率有点难待会做
     *
     * @param wave
     * @return
     */
    private Wave addRate(Wave wave) {
        return wave;
    }

    /**
     * 对频率大于目标频率的波抽样，
     * 使其达到目标频率
     *
     * @param wave
     * @return
     */
    private Wave minusRate(Wave wave) {
        int step = wave.getRate() / targetRate;
        double[] data = ArrayUtil.sampling(wave.getData(), step);
        return new RawWave(data, targetRate);
    }

}
