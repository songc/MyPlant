package com.songc.util;

/**
 * Created By @author songc
 * on 2017/11/17
 */
public class ExponentFitUtil {

    /**
     * y = a * exp(b * x)
     *
     * @param params 参数[a,b]
     * @param x      x的数组
     * @return y的数组
     */
    public static double[] getOneExpFuncValue(double[] params, double[] x) {
        int len = x.length;
        double[] y = new double[len];
        for (int i = 0; i < len; i++) {
            y[i] = params[0] * Math.exp(params[1] * x[i]);
        }
        return y;
    }

    /**
     * y = a * exp(b * x) + c * exp(d * x)
     *
     * @param params [a,b,c,d]
     * @param x      x的数组
     * @return y的数组
     */
    public static double[] getDualExpFuncValue(double[] params, double[] x) {
        int len = x.length;
        double[] y = new double[len];
        for (int i = 0; i < len; i++) {
            y[i] = params[0] * Math.exp(params[1] * x[i]) + params[2] * Math.exp(params[3] * x[i]);
        }
        return y;
    }

    /**
     * 生成函数 y=f(x)的 x 的double数组
     *
     * @param len   x的长度
     * @param start x的起始值。步长为1递增。
     * @return x的数组
     */
    public static double[] getX(int len, int start) {
        double[] x = new double[len];
        for (int i = 0; i < len; i++) {
            x[i] = i + start;
        }
        return x;
    }
}
