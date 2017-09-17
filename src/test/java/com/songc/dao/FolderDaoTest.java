package com.songc.dao;

import com.songc.entity.Folder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FolderDaoTest {

    @Autowired
    private FolderDao folderDao;

    @Test
    public void findAllByParentId() throws Exception {
        Long id = 100L;
        Folder folder = new Folder("songc", id);
        Folder folder1 = new Folder("songc2", id);
        folderDao.save(folder);
        folderDao.save(folder1);
        List<Folder> folders = folderDao.findAllByParentId(id);
        assertEquals(2, folders.size());
        assertEquals(id, folders.get(0).getParentId());
    }

}