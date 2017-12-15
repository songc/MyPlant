package com.songc.core.image;

import com.songc.dto.HbaseFileWithContentDTO;
import com.songc.util.ImageUtil;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created By @author songc
 * on 2017/11/3
 */
public class TiffImageTest {

    @Test
    public void test() throws IOException {
        String path = "F:\\Download\\software\\彩图\\1011165442.tiff";
        BufferedImage image = ImageIO.read(new File(path));
        Raster raster = image.getData();
        System.out.println(raster.getDataElements(2, 3, 400, 500, null));
        System.out.println(image.getWidth());
        System.out.println(image.getHeight());
        String[] readerFormat = ImageIO.getReaderFormatNames();
        String[] writerFormat = ImageIO.getWriterFormatNames();
        System.out.println(Arrays.asList(readerFormat));
        System.out.println(Arrays.asList(writerFormat));
    }

    @Test
    public void getGray() throws IOException {
        String path = "F:\\Download\\software\\彩图\\1011165442.tiff";
        BufferedImage bufferedImage = ImageIO.read(new File(path));
        BufferedImage grayImage = ImageUtil.convert2Gray(bufferedImage);
        File newFile = new File("C:\\Users\\songc\\Desktop\\MouseWithoutBorders\\3.tiff");
        ImageIO.write(grayImage, "tiff", newFile);
    }

    @Test
    public void getBufferedImage() throws IOException {
        String path = "F:\\Download\\software\\彩图\\1011165442.tiff";
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        Long length = file.length();
        byte[] filecontent = new byte[length.intValue()];
        in.read(filecontent);
        in.close();
        TiffImage image = new TiffImage(filecontent, file.getName());
        File newFile = new File("C:\\Users\\songc\\Desktop\\MouseWithoutBorders\\4.tiff");
        ImageIO.write(image.getImage(), "tiff", newFile);
    }

    @Test
    public void getRegionGaryAveage() throws IOException {
        int width = 50;
        int height = 50;
        String path = "F:\\Download\\software\\彩图\\1011165442.tiff";
        BufferedImage bufferedImage = ImageIO.read(new File(path));
        BufferedImage grayImage = ImageUtil.convert2Gray(bufferedImage);
        int[] data = new int[2500];
        int[] data2;
        data2 = grayImage.getRGB(0, 0, width, height, data, 0, width);
        int sum = 0;
        for (int i : data2) {
            System.out.println(i & 0xff);
            sum = sum + (i & 0xff);
        }
        System.out.println(data2.length);
        System.out.println(sum);
        System.out.println(sum / (width * height));
    }

    @Test
    public void getPng() throws IOException {
        String path = "F:\\Download\\software\\彩图\\1011165442.tiff";
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        Long length = file.length();
        byte[] fileContent = new byte[length.intValue()];
        in.read(fileContent);
        in.close();
        HbaseFileWithContentDTO hbaseFile = new HbaseFileWithContentDTO();
        hbaseFile.setContent(fileContent);
        hbaseFile.setName(file.getName());
        TiffImage image = new TiffImage(hbaseFile);
        ImageIO.write(image.getImage(), "png", new File("C:\\Users\\songc\\Desktop\\restful\\1.png"));
    }

    @Test
    public void getAllRegionGrayAverage() throws IOException {
        String path = "F:\\Download\\software\\彩图\\1011165442.tiff";
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        Long length = file.length();
        byte[] filecontent = new byte[length.intValue()];
        in.read(filecontent);
        in.close();
        TiffImage image = new TiffImage(filecontent, file.getName());
        double[] averageValue = image.getAllRegionGrayAverage(50, 50);
        System.out.println(averageValue.length);
        System.out.println(Arrays.toString(averageValue));
    }

}