package com.songc.service.imp;

import com.songc.entity.HbaseFile;
import com.songc.service.HbaseService;
import com.songc.service.MultipartFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultipartFileServiceImplTest {

    @MockBean
    private HbaseService hbaseService;
    @Autowired
    private MultipartFileService multipartFileService;

    @Test
    public void save() throws Exception {
        Long id = 100L;
        HbaseFile hbaseFile = new HbaseFile();
        hbaseFile.setParentId(id);
        given(hbaseService.save(any(HbaseFile.class))).willReturn(hbaseFile);
        MultipartFile multipartFile = mock(MultipartFile.class);
        HbaseFile hbaseFile1 = multipartFileService.save(id, multipartFile);
        assertEquals(id, hbaseFile.getParentId());
    }

    @Test
    public void save1() throws Exception {
        Long id = 100L;
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        HbaseFile hbaseFile = new HbaseFile();
        hbaseFile.setParentId(id);
        hbaseFiles.add(hbaseFile);
        given(hbaseService.save(hbaseFiles)).willReturn(hbaseFiles);
        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(mock(MultipartFile.class));
        List<HbaseFile> hbaseFiles1 = multipartFileService.save(id, multipartFiles);
        assertEquals(1, hbaseFiles1.size());
        assertEquals(id, hbaseFiles1.get(0).getParentId());
    }

}