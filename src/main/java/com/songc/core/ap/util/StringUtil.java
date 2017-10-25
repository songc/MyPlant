package com.songc.core.ap.util;

import java.util.ArrayList;

/**
 * Created by songc on 2/14/2017.
 */
public class StringUtil {
    public static String[] toStrArray(String str) {
        return str.split("\n");
    }

    public static double[] toDoubleArray(String str) {
        ArrayList<Double> result = new ArrayList<Double>();
        String[] strings = str.split(",");
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] != "") {
                result.add(Double.parseDouble(strings[i]));
            }
        }
        double[] finalResult = new double[result.size()];
        int i = 0;
        for (double tmp :
                result) {
            finalResult[i] = result.get(i);
            i++;
        }
        return finalResult;

    }

    public static double[][] toTwnDimArray(String str) {
        String[] string = toStrArray(str);
        int length = string.length;
        double[][] result = new double[length][];
        for (int i = 0; i < length; i++) {
            result[i] = toDoubleArray(string[i]);
        }
        return result;
    }

    public static String[][] getTwoDimeString(String[] readString) {
        String[][] str2 = new String[readString.length][];
        int i = 0;
        for (String tmpString :
                readString) {
            str2[i] = tmpString.split(",");
//			for (String tmp :
//					str2[i]){
//				System.out.println(tmp);
//			}
            i++;
        }
        return str2;
    }

    public static double[][] getDouble(String[][] arrays) {
        double[][] result = new double[arrays.length][arrays[0].length];
        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                result[i][j] = Double.parseDouble(arrays[i][j]);
//                System.out.println(result[i][j]);
            }
        }
        return result;
    }

    public static double[] getDouble(String[] arrays) {
        ArrayList<Double> result = new ArrayList<Double>();
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != "") {
                result.add(Double.parseDouble(arrays[i]));
            }
        }
        double[] finalResult = new double[result.size()];
        int i = 0;
        for (double tmp :
                result) {
            finalResult[i] = result.get(i);
            i++;
        }
        return finalResult;
    }

    public static double[][] toDimDouble(String[] str) {
        int length = str.length;
        double[][] result = new double[length][];
        for (int i = 0; i < length; i++) {
            result[i] = toDoubleArray(str[i]);
        }
        return result;
    }

    public static ArrayList getSubList(ArrayList arrayList, int start, int ends) {
        ArrayList result = new ArrayList();
        for (int i = start; i < ends; i++) {
            result.add(arrayList.get(i));
        }
        return result;
    }

    public static ArrayList getListFromArray(double[] arrays) {
        ArrayList result = new ArrayList();
        for (double tmp :
                arrays) {
            result.add(tmp + "");
        }
        return result;
    }
}
