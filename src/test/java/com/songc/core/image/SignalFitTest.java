package com.songc.core.image;

import com.songc.util.ExponentFitUtil;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created By @author songc
 * on 2017/11/16
 */
public class SignalFitTest {
    private double[] data = {
            -27.48, -27.48, -27.48, -27.48, -27.48, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -26.87, -27.48, -26.87, -26.87, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -27.48, -27.48, -27.48, -28.09, -28.09, -28.09, -28.09, -28.09, -28.09, -28.7, -38.47, -28.7, -26.26, -25.65, -25.04, -26.87, -26.87, -26.87, -26.87, -27.48, -27.48, -27.48, -28.09, -28.09, -28.09, -28.7, -28.7, -29.31, -29.31, -29.31, -29.92, -29.92, -29.92, -30.53, -30.53, -30.53, -30.53, -30.53, -30.53, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92, -29.92
    };

    @Test
    public void fit() {
        double[] x = ExponentFitUtil.getX(data.length, 1);
        final double coeffs[] = SignalFit.fitOneExponent(x, data);
        System.out.println(Arrays.toString(coeffs));
        System.out.println(data.length);
    }

    @Test
    public void fitTwo() {
        double[] x = ExponentFitUtil.getX(data.length, 1);
        final double[] coefs = SignalFit.fitDualExponent(x, data);
        System.out.println(Arrays.toString(coefs));
    }
}
