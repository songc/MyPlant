package com.songc.core.ap.detect.wave;

import com.songc.core.ap.util.MathMethods;

/**
 * 查找动作电位和模板中波峰的位置
 * 方便转换使提取的电位波形和模板的波形长度一致
 * Created by songc on 2/16/2017.
 */
public class Peak {
    public static int findPeak(Wave wave) {
        int rate = wave.rate;
        double[] data = wave.data;
        int peakInData = -1;
        //求出data中的最小值
        double minData = MathMethods.minInArrays(data);
        for (int i = 1; i < data.length - 1; i++) {
            if (data[i - 1] < data[i] && data[i] >= data[i + 1] && i >= rate && Math.abs(data[i] - data[0]) >= 6
                    && (data[i] == minData)) {
                peakInData = i;
                break;
            } else if (data[i - 1] > data[i] && data[i] <= data[i + 1] && i >= rate && Math.abs(data[i] - data[0]) >= 6 &&
                    (data[i] == minData)) {
                peakInData = i;
                break;
            } else {
                peakInData = -1;
            }
        }
        return peakInData;
    }
}
