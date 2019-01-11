package com.songc.util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By @author songc
 * on 2017/11/3
 */
public class ImageUtil {

    /**
     * 将RGB图像转换为灰度图像
     *
     * @param bufferedImage 原始图像文件
     * @return 转换后的灰度图像
     */
    public static BufferedImage convert2Gray(BufferedImage bufferedImage) {
        BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                final int color = bufferedImage.getRGB(i, j);
                final int r = (color >> 16) & 0xff;
                final int g = (color >> 8) & 0xff;
                final int b = color & 0xff;
                int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
                int newPixel = colorToRGB(255, gray, gray, gray);
                grayImage.setRGB(i, j, newPixel);
            }
        }
        return grayImage;
    }

    /**
     * 计算图像的区域的灰度平均值
     * @param image 图像
     * @param startX 区域的起点X位置，区域的左上角和图片左上角的水平距离
     * @param startY 区域的起点Y位置，区域的左上角和图片左上角的垂直距离
     * @param width 区域的宽度
     * @param height 区域的高度
     * @return 区域的灰度平均值
     */
    public static double getRegionGrayAverage(BufferedImage image, int startX, int startY, int width, int height) {
        int[] data = new int[width * height];
        data = image.getRGB(startX, startY, width, height, data, 0, width);
        int sum = 0;
        for (int i : data) {
            sum += i & 0xff;
        }
        return sum * 1.0 / data.length;
    }

    /**
     * 对矩阵进行转置
     *
     * @param doubles 需要转置的矩阵
     * @return 转置后的矩阵
     */
    public static List<double[]> getTranspose(List<double[]> doubles) {
        int len = doubles.size();
        int lenArray = doubles.get(0).length;
        List<double[]> result = new ArrayList<>();
        for (int i = 0; i < lenArray; i++) {
            double[] array = new double[len];
            int j = 0;
            for (double[] aDouble : doubles) {
                array[j++] = aDouble[i];
            }
            result.add(array);
        }
        return result;
    }

    /**
     * rgb转换为int类型
     * @param alpha 透明度
     * @param red  R的值
     * @param green  G的值
     * @param blue B的值
     * @return 转换后的int类型的值
     */
    private static int colorToRGB(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;
        return newPixel;
    }
}
