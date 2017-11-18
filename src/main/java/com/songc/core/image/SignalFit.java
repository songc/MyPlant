package com.songc.core.image;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By @author songc
 * on 2017/11/17
 */
public class SignalFit {

    /**
     * y = a * exp(b * x)
     * 返回单指数的拟合后的参数
     *
     * @return [a, b]
     */
    public static double[] fitOneExponent(double[] x, double[] y) {
        List<WeightedObservedPoint> points = new ArrayList<>();
        for (int i = 0, k = x.length; i < k; i++) {
            WeightedObservedPoint point = new WeightedObservedPoint(1.0, x[i], y[i]);
            points.add(point);
        }
        return new OneExponentFit().fit(points);
    }

    /**
     * y = a * exp(b * x) + c * exp(d * x)
     * 获取双指数拟合后的参数
     *
     * @return [a, b, c, d]
     */
    public static double[] fitDualExponent(double[] x, double[] y) {
        List<WeightedObservedPoint> points = new ArrayList<>();
        for (int i = 0, k = x.length; i < k; i++) {
            WeightedObservedPoint point = new WeightedObservedPoint(1.0, x[i], y[i]);
            points.add(point);
        }
        return new DualExponentFit().fit(points);
    }
}
