package com.songc.core.ap.detect.wave;

/**
 * Created by songc on 2/17/2017.
 */
public class APInRawWave {
    int startIndex;
    int peakIndex;
    int endIndex;
    double[] data;

    public APInRawWave() {
    }

    ;


    public APInRawWave(double[] data, int startIndex, int peakIndex, int endIndex) {
        this.data = data;
        this.startIndex = startIndex;
        this.peakIndex = peakIndex;
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getPeakIndex() {
        return peakIndex;
    }

    public void setPeakIndex(int peakIndex) {
        this.peakIndex = peakIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }


    public double[] getData() {
        return data;
    }


    public void setData(double[] data) {
        this.data = data;
    }

}
