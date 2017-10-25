package com.songc.core.ap.detect.wave;

import com.songc.core.ap.util.MathMethods;

/**
 * Created by songc on 2/14/2017.
 */
public class MinusWave extends Wave {
    public MinusWave(Wave wave) {
        double[] data = wave.data;
        if (MathMethods.maxInArrays(data) == 0) {
            data = MathMethods.arraysMinus(data, MathMethods.minInArrays(data));
        }
        this.data = data;
        this.rate = wave.rate;
    }
}
