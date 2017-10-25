package com.songc.core.ap.detect.wave;

import com.songc.core.ap.util.MathMethods;

import java.util.Arrays;

/**
 * 对波形进行低通滤波。
 * Created by songc on 2/14/2017.
 */
public class LowFilter {
    private Wave rawWave;
    private final static int mark = 1;
    private final double[] filterAugment1 = {1, 0, 0, 0, 0, 0, -2, 0, 0, 0, 0, 0, 1};
    private final double[] filterAugment2 = {1, -2, 1};
    private final double[] filterAugment3 = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public LowFilter(Wave rawWave) {
        this.rawWave = rawWave;
    }

    Wave getLowFilterWave() {
        double[] h_1 = MathMethods.filter(this.filterAugment1, this.filterAugment2, this.filterAugment3);
        double[] afterFilterSignal = MathMethods.conv(h_1, this.rawWave.data);
        double[] afterSecondFilterSignal = Arrays.copyOfRange(afterFilterSignal, 13, (int) (afterFilterSignal.length * 0.95));

        double[] finalSignal;
        if (mark == 0) {
            finalSignal = MathMethods.arraysDivide(afterSecondFilterSignal, MathMethods.maxInArrays(MathMethods.arraysAbs(afterSecondFilterSignal)));
        } else if (mark == 1) {
            finalSignal = MathMethods.arraysDivide(afterSecondFilterSignal, 36);
        }
        return new LowFilterWave(finalSignal, rawWave.rate);
    }
}
