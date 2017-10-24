package com.songc.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songc
 */
public class SmoothFilter {
    public static List<double[]> filter(List<Double[]> insList, int windowWidth) {
        List<double[]> res = new ArrayList<>();
        for (Double[] ins : insList) {
            int len = ins.length;
            double sum = 0;
            double[] ret = new double[len];
            for (int i = 0; i < len; ++i) {
                if (i < windowWidth) {
                    // 前windowWidth个
                    sum += ins[i];
                    ret[i] = sum / (i + 1);
                } else if (i > (len - windowWidth)) {
                    // 后windowWidth个
                    sum -= ins[i - windowWidth];
                    ret[i] = sum / (len - i);
                } else {
                    // 中间部分
                    sum -= ins[i - windowWidth];
                    sum += ins[i];
                    ret[i] = sum / windowWidth;
                }
            }
            res.add(ret);
        }

        return res;
    }
}
