package com.songc.core.ap.util;

/**
 * Created by songc on 2/15/2017.
 */
public class ArrayUtil {
    public static double[][] transpose(double[][] data) {
        int rows = data.length;
        int columns = data[0].length;
        double[][] result = new double[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                result[i][j] = data[j][i];
            }
        }
        return result;
    }

    public static double[] sampling(double[] data, int step) {
        double[] result = new double[data.length / step];
        for (int i = 0, j = 0; i < data.length; i += step, j++) {
            result[j] = data[i];
        }
        return result;
    }
}
