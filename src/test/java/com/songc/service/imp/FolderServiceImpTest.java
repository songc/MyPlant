package com.songc.service.imp;

import com.songc.dao.FolderDao;
import com.songc.entity.Folder;
import com.songc.entity.HbaseFile;
import com.songc.service.FolderService;
import com.songc.service.HbaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FolderServiceImpTest {

    @MockBean
    private FolderDao folderDao;

    @MockBean
    private HbaseService hbaseService;

    @Autowired
    private FolderService folderService;

    @Test
    public void save() throws Exception {
        Long id = 100L;
        Folder folder = new Folder();
        folder.setFolderId(id);
        given(folderDao.save(folder)).willReturn(folder);
        Folder folder1 = folderService.save(folder);
        assertEquals(id, folder1.getFolderId());
    }

    @Test
    public void find() throws Exception {
        Long id = 100L;
        Folder folder = new Folder();
        folder.setFolderId(id);
        given(folderDao.findOne(id)).willReturn(folder);
        Folder folder1 = folderService.find(id);
        assertEquals(id, folder1.getFolderId());
    }

    @Test
    public void findSubFolder() throws Exception {
        Long id = 100L;
        List<Folder> folders = new ArrayList<>();
        folders.add(new Folder());
        given(folderDao.findAllByParentId(id)).willReturn(folders);
        List<Folder> folders1 = folderService.findSubFolder(id);
        assertEquals(folders.size(), folders1.size());
    }

    @Test
    public void save1() throws Exception {
        Long id = 100L;
        String name = "songc";
        Folder folder = new Folder();
        folder.setParentId(id);
        folder.setName(name);
        given(folderDao.save(any(Folder.class))).willReturn(folder);
        Folder folder1 = folderService.save(folder);
        assertEquals(id, folder1.getParentId());
        assertEquals(name, folder1.getName());
    }

    @Test
    public void findSubFile() throws Exception {
        Long id = 100L;
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        hbaseFiles.add(new HbaseFile());
        given(hbaseService.findByParentId(id)).willReturn(hbaseFiles);
        List<HbaseFile> hbaseFiles1 = folderService.findSubFile(id);
        assertEquals(hbaseFiles.size(), hbaseFiles1.size());
    }

    @Test
    public void delete() throws Exception {
        Long id = 100L;
        folderService.delete(id);
        verify(folderDao).delete(any(Long.TYPE));
        verify(folderDao).delete(anyList());
    }

}