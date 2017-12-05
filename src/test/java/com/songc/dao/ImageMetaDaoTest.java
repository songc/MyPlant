package com.songc.dao;

import com.songc.entity.meta.ImageMeta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created By @author songc
 * on 2017/12/5
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ImageMetaDaoTest {
    private Long userId = 100L;

    @Autowired
    private ImageMetaDao imageMetaDao;

    @Test
    public void findByUserId() throws Exception {
        List<ImageMeta> imageMetaList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ImageMeta imageMeta = new ImageMeta();
            imageMeta.setUserId(userId);
            imageMeta.setName("songc" + i);
            imageMetaList.add(imageMeta);
        }
        imageMetaDao.save(imageMetaList);
        List<ImageMeta> imageMetaList1 = imageMetaDao.findByUserId(userId);
        assertEquals(imageMetaList.size(), imageMetaList1.size());
        imageMetaDao.delete(imageMetaList1.get(0).getId());
        List<ImageMeta> imageMetaList2 = imageMetaDao.findByUserId(userId);
        assertEquals(imageMetaList2.size(), imageMetaList1.size() - 1);
    }

}