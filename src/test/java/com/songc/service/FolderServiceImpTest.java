package com.songc.service;

import com.songc.dao.FolderDao;
import com.songc.entity.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FolderServiceImpTest {

    @MockBean
    private FolderDao folderDao;

    public void testSave() {
        Folder folder = mock(Folder.class);
        Folder folder1 = mock(Folder.class);
        given(this.folderDao.save(folder)).willReturn(folder1);

    }
}
