package com.songc.core.ap.detect.wave;

/**
 * Created by songc on 2/14/2017.
 */
public class Peaks {
    double[] pks;
    int[] locs;

    public Peaks(double[] pks, int[] locs) {
        this.locs = locs;
        this.pks = pks;
    }

    public void setLocs(int[] locs) {
        this.locs = locs;
    }

    public int[] getLocs() {
        return locs;
    }

    public void setPks(double[] pks) {
        this.pks = pks;
    }

    public double[] getPks() {
        return pks;
    }
}
