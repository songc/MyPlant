package com.songc.core.ap.detect.wave;

/**
 * Created by songc on 2/14/2017.
 */
public class RawWave extends Wave {
    public RawWave(double[] data, int rate) {
        super();
        this.data = data;
        this.rate = rate;
    }
}
