package com.songc.core.ap.detect.wave;

import com.songc.core.ap.util.MathMethods;

/**
 * Created by songc on 2/14/2017.
 */
public class DivideWave extends Wave {
    public DivideWave(Wave wave) {
        double[] data = wave.data;
        data = MathMethods.arraysDivide(data, MathMethods.maxInArrays(MathMethods.arraysAbs(data)));
        this.data = data;
        this.rate = wave.rate;
    }
}
