package com.songc.core.ap.util;

import java.text.DecimalFormat;

/**
 * 实现MATLAB中的一些函数
 * Created by songc on 2016/8/16.
 */
public class MathMethods {
    public static double[] filter(double[] b, double[] a, double[] x) {
        double[] filter = null;
        double[] a1 = getRealArrayScalarDiv(a, a[0]);
        double[] b1 = getRealArrayScalarDiv(b, a[0]);
        int sx = x.length;
        filter = new double[sx];
        filter[0] = b1[0] * x[0];
        for (int i = 1; i < sx; i++) {
            filter[i] = 0.0;
            for (int j = 0; j <= i; j++) {
                int k = i - j;
                if (j > 0) {
                    if ((k < b1.length) && (j < x.length)) {
                        filter[i] += b1[k] * x[j];
                    }
                    if ((k < filter.length) && (j < a1.length)) {
                        filter[i] -= a1[j] * filter[k];
                    }
                } else {
                    if ((k < b1.length) && (j < x.length)) {
                        filter[i] += (b1[k] * x[j]);
                    }
                }
            }
        }
        return filter;
    }

    public static double[] conv(double[] a, double[] b) {
        double[] c = null;
        int na = a.length;
        int nb = b.length;
        if (na > nb) {
            if (nb > 1) {
                c = new double[na + nb - 1];
                for (int i = 0; i < c.length; i++) {
                    if (i < a.length) {
                        c[i] = a[i];
                    } else {
                        c[i] = 0.0;
                    }
                }
                a = c;
            }
            c = filter(b, new double[]{1.0}, a);
        } else {
            if (na > 1) {
                c = new double[na + nb - 1];
                for (int i = 0; i < c.length; i++) {
                    if (i < b.length) {
                        c[i] = b[i];
                    } else {
                        c[i] = 0.0;
                    }
                }
                b = c;
            }
            c = filter(a, new double[]{1.0}, b);
        }
        return c;
    }

    public static double[] deconv(double[] b, double[] a) {
        double[] q = null;
        int sb = b.length;
        int sa = a.length;
        if (sa > sb) {
            return q;
        }
        double[] zeros = new double[sb - sa + 1];
        for (int i = 1; i < zeros.length; i++) {
            zeros[i] = 0.0;
        }
        zeros[0] = 1.0;
        q = filter(b, a, zeros);
        return q;
    }

    public static double[] deconvRes(double[] b, double[] a) {
        double[] r = null;
        r = getRealArraySub(b, conv(a, deconv(b, a)));
        return r;
    }

    public static double[] getRealArraySub(double[] dSub0, double[] dSub1) {
        double[] dSub = null;
        if ((dSub0 == null) || (dSub1 == null)) {
            throw new IllegalArgumentException("The array must be defined or diferent to null");
        }
        if (dSub0.length != dSub1.length) {
            throw new IllegalArgumentException("Arrays must be the same size");
        }
        dSub = new double[dSub1.length];
        for (int i = 0; i < dSub.length; i++) {
            dSub[i] = dSub0[i] - dSub1[i];
        }
        return dSub;
    }

    public static double[] getRealArrayScalarDiv(double[] dDividend, double dDivisor) {
        if (dDividend == null) {
            throw new IllegalArgumentException("The array must be defined or diferent to null");
        }
        if (dDividend.length == 0) {
            throw new IllegalArgumentException("The size array must be greater than Zero");
        }
        double[] dQuotient = new double[dDividend.length];

        for (int i = 0; i < dDividend.length; i++) {
            if (!(dDivisor == 0.0)) {
                dQuotient[i] = dDividend[i] / dDivisor;
            } else {
                if (dDividend[i] > 0.0) {
                    dQuotient[i] = Double.POSITIVE_INFINITY;
                }
                if (dDividend[i] == 0.0) {
                    dQuotient[i] = Double.NaN;
                }
                if (dDividend[i] < 0.0) {
                    dQuotient[i] = Double.NEGATIVE_INFINITY;
                }
            }
        }
        return dQuotient;
    }

    public static int search(double[] arrays, double goal) {
        int result = -1;
        int length = arrays.length;
        for (int i = 0; i < length; i++) {
            if (goal == arrays[i]) {
                result = i;
                return result;
            }
        }
        return result;
    }

    //求数组中的最大值
    public static double maxInArrays(double[] arrays) {
        double max = arrays[0];
        for (double tmp :
                arrays) {
            if (max < tmp) {
                max = tmp;
            }
        }
        return max;
    }

    public static double minInArrays(double[] arrays) {
        double min = arrays[0];
        for (double tmp :
                arrays) {
            if (min > tmp) {
                min = tmp;
            }
        }
        return min;
    }

    public static double[] arraysMinus(double[] arrays, double minus) {
        double[] result = new double[arrays.length];
        int i = 0;
        for (double tmp :
                arrays) {
            result[i++] = tmp - minus;
        }
        return result;
    }

    public static double[] arraysDivide(double[] arrays, double divide) {
        double[] result = new double[arrays.length];
        int i = 0;
        for (double tmp :
                arrays) {
            result[i++] = tmp / divide;
        }
        return result;
    }

    public static double[] arraysAbs(double[] arrays) {
        double[] result = new double[arrays.length];
        int i = 0;
        for (double tmp :
                arrays) {
            if (tmp < 0) {
                result[i++] = -tmp;
            } else {
                result[i++] = tmp;
            }
        }
        return result;
    }

    public static double arraysMean(double[] arrays) {
        double result = 0;
        for (double tmp :
                arrays) {
            result += tmp;
        }
        result = result / arrays.length;
        return result;
    }

    public static String[] arraysDecimal(double[] arrays, int scale) {
        int i = 0;
        String[] result = new String[arrays.length];
        StringBuilder pattern = new StringBuilder("0");
        if (scale > 0) {
            pattern.append(".");
        }
        if (scale > 9) {
            System.out.println("输入精度错误");
        }
        for (int j = 0; j < scale; j++) {
            pattern.append("0");
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern.toString());
        for (double tmp :
                arrays) {
            result[i++] = decimalFormat.format(tmp);
        }
        return result;
    }

    public static double[] arraysAccuracy(double[] arrays, int scale) {
        double[] result = new double[arrays.length];
        int i = 0;
        for (double tmp :
                arrays) {
            result[i++] = Math.round(Math.pow(10, scale) * tmp) / Math.pow(10, scale);
        }
        return result;
    }

    public static String arraysToString(double[] arrays, int scale) {
        String[] dataString = MathMethods.arraysDecimal(arrays, scale);
        StringBuilder stringBuilder = new StringBuilder();
        for (String tmp :
                dataString) {
            stringBuilder.append(tmp);
            stringBuilder.append(",");
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }

    public static double[] creatArrays(double value, int length) {
        double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            result[i] = value;
        }
        return result;
    }

    public static double[] arraysMerge(double[] first, double[] second) {
        double[] result = new double[first.length + second.length];
        for (int i = 0; i < first.length; i++) {
            result[i] = first[i];
        }
        for (int j = first.length; j < first.length + second.length; j++) {
            result[j] = second[j - first.length];
        }
        return result;
    }

    public static double[][] arraysTranspose(double[][] arrays) {
        double[][] finalData = new double[arrays[0].length][arrays.length];
        for (int j = 0; j < arrays[0].length; j++) {
            for (int k = 0; k < arrays.length; k++) {
                finalData[j][k] = arrays[k][j];
            }
        }
        return finalData;
    }

    public static int findPotion(double[] arrays, double object) {
        int result = -1;
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] == object) {
                result = i;
                return result;
            }
        }
        return result;
    }
}
