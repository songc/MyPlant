package com.songc.core.ap.detect.wave;

import com.songc.core.ap.util.MathMethods;

/**
 * Created by songc on 2/14/2017.
 */
public class AccuracyWave extends Wave {
    public AccuracyWave(Wave wave, int scale) {
        this.data = MathMethods.arraysAccuracy(wave.data, scale);
        this.rate = wave.rate;
    }
}
