package com.songc.core.image;

import com.songc.dto.HbaseFileWithContentDTO;
import com.songc.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created By @author songc
 * on 2017/11/3
 */
public class TiffImage {
    private byte[] content;
    private String rowKey;

    TiffImage(byte[] content, String rowKey) {
        this.content = content;
        this.rowKey = rowKey;
    }

    public TiffImage(HbaseFileWithContentDTO hbaseFile) {
        this.content = hbaseFile.getContent();
        this.rowKey = hbaseFile.getRowKey();
    }

    public BufferedImage getImage() {
        try {
            return ImageIO.read(new ByteArrayInputStream(content));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将图像转换为灰度图
     *
     * @return 转换后的灰度图像
     */
    private BufferedImage convert2Gray() {
        return ImageUtil.convert2Gray(this.getImage());
    }

    /**
     * 图片指定区域的灰度平均值
     * @param startX 区域的起点X位置，区域的左上角和图片左上角的水平距离
     * @param startY 区域的起点Y位置，区域的左上角和图片左上角的垂直距离
     * @param width 区域的宽度
     * @param height 区域的高度
     * @return 指定区域的灰度平均值
     */
    public double getRegionGrayAverage(int startX, int startY, int width, int height) {
        return ImageUtil.getRegionGrayAverage(this.convert2Gray(), startX, startY, width, height);
    }

    /**
     * 多区域的灰度平均值
     * @param regionWidth 区域的宽度
     * @param regionHeight 区域的高度
     * @return 图片的所有区域灰度平均值列表 （行优先）
     */
    public double[] getAllRegionGrayAverage(int regionWidth, int regionHeight) {
        BufferedImage image = this.convert2Gray();
        int width = image.getWidth();
        int height = image.getHeight();
        int wNum = width / regionWidth;
        int hNum = height / regionHeight;
        double[] result = new double[wNum * hNum];
        for (int i = 0; i < wNum; i++) {
            for (int j = 0; j < hNum; j++) {
                result[i * hNum + j] = ImageUtil.getRegionGrayAverage(image, regionHeight * i, regionHeight * j, regionWidth, regionHeight);
            }
        }
        return result;
    }
}
