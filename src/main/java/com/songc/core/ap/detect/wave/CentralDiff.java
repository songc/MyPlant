package com.songc.core.ap.detect.wave;

import com.songc.core.ap.util.MathMethods;

/**
 * pointNum 决定使用不同的差分方法。
 * Created by songc on 2/14/2017.
 */
public class CentralDiff {
    int pointNum; //选择差分的方法，有一点差分，3点差分，5点差分

    public CentralDiff(int pointNum) {
        this.pointNum = pointNum;
    }

    Wave getCentralDiffWave(Wave lowFilterWave) {
        double[] signal = lowFilterWave.data;
        int fs = lowFilterWave.rate;
        double[] finalSignal = new double[signal.length];
        int len = signal.length;
        if (pointNum == 1) {
            for (int i = 0; i < finalSignal.length - 1; i++) {
                finalSignal[i] = (signal[i + 1] - signal[i]) / fs;
            }
        } else if (pointNum == 3) {
            finalSignal[0] = (signal[0] * -3 + signal[1] * 4 - signal[2]) / (2 * fs);
            finalSignal[len - 1] = (signal[len - 3] - 4 * signal[len - 2] + 3 * signal[len - 1]) / (2 * fs);
            for (int i = 1; i < len - 1; i++) {
                finalSignal[i] = (signal[i + 1] - signal[i - 1]) / (2 * fs);
            }
        } else if (pointNum == 5) {
            for (int i = 0; i < len; i++) {
                if (i == 0) {
                    finalSignal[i] = (signal[i] * (-25) + 48 * signal[i + 1] - 36 * signal[i + 2] + 16 * signal[i + 3]
                            - 3 * signal[i + 4]) / (12 * fs);
                } else if (i == 1) {
                    finalSignal[i] = (signal[i - 1] * (-3) - 10 * signal[i] + 18 * signal[i + 1] - 6 * signal[i + 2] +
                            signal[i + 3]) / (12 * fs);
                } else if (i == len - 2) {
                    finalSignal[i] = (-signal[i - 3] + 6 * signal[i - 2] - 18 * signal[i - 1]
                            + 10 * signal[i] + 3 * signal[i + 1]) / (12 * fs);
                } else if (i == len - 1) {
                    finalSignal[i] = (3 * signal[i - 4] - 16 * signal[i - 3] + 36 * signal[i - 2]
                            - 48 * signal[i - 1] + 25 * signal[i]) / (12 * fs);
                } else {
                    finalSignal[i] = (signal[i - 2] - 8 * signal[i - 1] + 8 * signal[i + 1]
                            - signal[i + 2]) / (12 * fs);
                }
            }
        } else if (pointNum == 51) {
            double[] h_d = {-0.125, -0.25, 0, 0.25, 0.125};
            finalSignal = MathMethods.conv(signal, h_d);
        }
        return new CentralDiffWave(finalSignal, lowFilterWave.rate);
    }
}
