package com.songc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songc.dto.FileMeta;
import com.songc.dto.HbaseFileDTO;
import com.songc.dto.HbaseFileWithContentDTO;
import com.songc.entity.Dataset;
import com.songc.entity.HbaseFile;
import com.songc.entity.data.StatusEnum;
import com.songc.service.DatasetService;
import com.songc.service.MultipartFileService;
import com.songc.util.MapperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DatasetControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatasetService datasetService;

    @MockBean
    private MultipartFileService multipartFileService;


    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void save() throws Exception {
        Dataset dataset = new Dataset();
        given(this.datasetService.save(any(Dataset.class))).willReturn(dataset);
        this.mockMvc.perform(post("/dataset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dataset)))
                .andExpect(content().string(mapper.writeValueAsString(dataset)));
    }

    @Test
    public void getPageDatasetTest() throws Exception {
        Integer page = 1;
        Integer size = 10;
        Pageable pageable = new PageRequest(page, size);
        List<Dataset> datasets = new ArrayList<>();
        Page<Dataset> datasetPage = new PageImpl<>(datasets, pageable, 10);
        given(this.datasetService.getPageDataset(page, size)).willReturn(datasetPage);
        this.mockMvc.perform(get("/dataset").param("number", page.toString())
                .param("size", size.toString())).andExpect(content().string(mapper.writeValueAsString(datasetPage)));
    }

    @Test
    public void search() throws Exception {
        Integer page = 1;
        Integer size = 10;
        Pageable pageable = new PageRequest(page, size);
        List<Dataset> datasets = new ArrayList<>();
        Page<Dataset> datasetPage = new PageImpl<>(datasets, pageable, 10);
        given(this.datasetService.search("data", 1, 10)).willReturn(datasetPage);
        this.mockMvc.perform(get("/dataset/query")
                .param("keyWord", "data")
                .param("number", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(datasetPage)));
    }

    @Test
    public void update() throws Exception {
        Dataset dataset = new Dataset();
        given(this.datasetService.update(any(Dataset.class))).willReturn(dataset);
        this.mockMvc.perform(put("/dataset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dataset)))
                .andExpect(content().string(mapper.writeValueAsString(dataset)));
    }

    @Test
    public void findById() throws Exception {
        Dataset dataset = new Dataset();
        dataset.setUserId(1L);
        given(this.datasetService.findOne(any(Long.TYPE))).willReturn(dataset);
        this.mockMvc.perform(get("/dataset/1"))
                .andExpect(content().string(mapper.writeValueAsString(dataset)));
    }

    @Test
    public void delete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/dataset/1"))
                .andExpect(content().string(StatusEnum.SUCCESS.toString()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void save1() throws Exception {
        MockMultipartFile file = new MockMultipartFile("songc", "content".getBytes());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("environmentId", "100");
        params.add("softwareId", "100");
        params.add("sampleId", "100");
        params.add("imageMetaId", "100");
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        hbaseFiles.add(new HbaseFile());
        given(multipartFileService.save(anyLong(), anyList(), any(FileMeta.class))).willReturn(hbaseFiles);
        mockMvc.perform(fileUpload("/dataset/1/file").file(file).file(file)
                .params(params))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(MapperUtil.convert(hbaseFiles))));
    }

    @Test
    public void findFile() throws Exception {
        List<HbaseFileDTO> files = new ArrayList<>();
        given(datasetService.findFile(any(Long.TYPE))).willReturn(files);
        this.mockMvc.perform(get("/dataset/1/file"))
                .andExpect(content().string(mapper.writeValueAsString(files)));
    }

    @Test
    public void download() throws Exception {
        List<HbaseFileWithContentDTO> files = new ArrayList<>();
        given(datasetService.findWithContentFile(anyLong())).willReturn(files);
        this.mockMvc.perform(get("/dataset/1/zip?name=songc"))
                .andExpect(status().isOk());
    }
}
