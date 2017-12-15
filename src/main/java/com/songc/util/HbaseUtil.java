package com.songc.util;

import com.songc.core.image.TiffImage;
import com.songc.dto.HbaseFileWithContentDTO;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.Instant;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class HbaseUtil {
    private static Long HF_ID = 0L;

    public static String convertRowKey(Long parentId) {
        return StringUtils.reverse(String.format("%016d", parentId))
                + (Long.MAX_VALUE - Instant.now().toEpochMilli())
                + String.format("%016d", HF_ID++);
    }

    public static void convert2Zip(HttpServletResponse response, List<HbaseFileWithContentDTO> hbaseFileList, String name) throws IOException {
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + name + ".zip" + "\"");
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
        int i = 1;
        for (HbaseFileWithContentDTO hbaseFile : hbaseFileList) {
            zipOutputStream.putNextEntry(new ZipEntry(i++ + "_" + hbaseFile.getName()));
            OutputStream outputStream = new DataOutputStream(zipOutputStream);
            outputStream.write(hbaseFile.getContent());
            zipOutputStream.closeEntry();
        }
        zipOutputStream.close();
    }

    public static byte[] getPngBytes(HbaseFileWithContentDTO hbaseFile) throws IOException {
        TiffImage tiffImage = new TiffImage(hbaseFile);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(tiffImage.getImage(), "png", out);
        return out.toByteArray();
    }
}
