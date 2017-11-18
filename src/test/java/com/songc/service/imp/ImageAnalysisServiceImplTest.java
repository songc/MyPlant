package com.songc.service.imp;

import com.songc.dto.MultiRegionSignalDTO;
import com.songc.dto.SingleRegionSignalDTO;
import com.songc.entity.HbaseFile;
import com.songc.service.HbaseService;
import com.songc.service.ImageAnalysisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;

/**
 * Created By @author songc
 * on 2017/11/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageAnalysisServiceImplTest {
    private static final String PATH = "F:\\Download\\software\\彩图";
    private List<HbaseFile> hbaseFileList;

    @Autowired
    ImageAnalysisService imageAnalysisService;

    @MockBean
    HbaseService hbaseService;

    {
        File file = new File(PATH);
        File[] files = file.listFiles();
        hbaseFileList = Arrays.stream(files).map(file1 -> {
            HbaseFile hbaseFile = new HbaseFile();
            long length = file1.length();
            try {
                InputStream in = new FileInputStream(file1);
                byte[] content = new byte[(int) length];
                in.read(content);
                in.close();
                hbaseFile.setContent(content);
                hbaseFile.setName(file1.getName());
                return hbaseFile;
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return hbaseFile;
        }).collect(Collectors.toList());
    }

    @Test
    public void singleRegion() throws Exception {

        given(this.hbaseService.findByParentId(anyLong())).willReturn(hbaseFileList);
        SingleRegionSignalDTO result = imageAnalysisService.singleRegion(100L, 50, 50, 50, 50);

        assertEquals(hbaseFileList.size(), result.getF().length);
        System.out.println(result.getF().length);
        System.out.println(Arrays.toString(result.getF()));
        System.out.println(Arrays.toString(result.getF0()));
    }

    @Test
    public void multiRegion() throws Exception {
        given(this.hbaseService.findByParentId(anyLong())).willReturn(hbaseFileList);
        MultiRegionSignalDTO result = imageAnalysisService.multiRegion(100L, 50, 50);
        assertEquals(hbaseFileList.size(), result.getF().get(0).length);
        System.out.println(result.getF().get(0).length);
        result.getF().stream().map(Arrays::toString).forEach(System.out::println);
        result.getF0().stream().map(Arrays::toString).forEach(System.out::println);
    }

}