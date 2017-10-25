package com.songc.core.ap.detect.wave;

import java.util.Arrays;

/**
 * Created by songc on 2/14/2017.
 */
public class FindPeaks {
    private double TH;

    public FindPeaks(double TH) {
        this.TH = TH;
    }

    Peaks getPeaks(Wave wave) {
        double[] signal = wave.data;
        double[] pks = new double[signal.length];
        int[] locs = new int[signal.length];
        int j = 0;
        for (int i = 1; i < signal.length - 1; i++) {
            if (signal[i] * signal[i - 1] >= 0 && signal[i] * signal[i + 1] >= 0) {
                if (Math.abs(signal[i]) > Math.abs(signal[i - 1]) &&
                        Math.abs(signal[i]) > Math.abs(signal[i + 1]) &&
                        Math.abs(signal[i]) > TH) {
                    pks[j] = signal[i];
                    locs[j] = i;
                    j++;
                    i++;
                }

            } else if (signal[i] * signal[i - 1] >= 0 &&
                    Math.abs(signal[i]) > Math.abs(signal[i - 1]) &&
                    signal[i] * signal[i + 1] <= 0 && Math.abs(signal[i]) > TH) {
                pks[j] = signal[i];
                locs[j] = i;
                j++;
            } else if (signal[i] * signal[i - 1] <= 0 && signal[i] * signal[i + 1] >= 0 &&
                    Math.abs(signal[i]) > Math.abs(signal[i + 1]) && Math.abs(signal[i]) > TH) {
                pks[j] = signal[i];
                locs[j] = i;
                j++;
            } else if (signal[i] * signal[i - 1] <= 0 && signal[i] * signal[i + 1] <= 0 &&
                    Math.abs(signal[i]) > TH) {
                pks[j] = signal[i];
                locs[j] = i;
                j++;
            }

        }
        double[] finalPks = Arrays.copyOfRange(pks, 0, j);
        int[] finalLocs = Arrays.copyOfRange(locs, 0, j);
        return new Peaks(finalPks, finalLocs);
    }
}
