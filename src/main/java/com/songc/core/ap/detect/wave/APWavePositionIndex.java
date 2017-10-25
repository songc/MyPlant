package com.songc.core.ap.detect.wave;

/**
 * Created by songc on 2/14/2017.
 */
class APWavePositionIndex {
    int[] leftIndex;
    int[] peaksIndex;
    int[] rightIndex;

    APWavePositionIndex(int[] leftIndex, int[] peaksIndex, int[] rightIndex) {
        this.leftIndex = leftIndex;
        this.peaksIndex = peaksIndex;
        this.rightIndex = rightIndex;
    }
}
