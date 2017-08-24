package com.songc.dao;

import com.songc.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by songc on 4/27/2017.
 */
public interface FolderDao extends JpaRepository<Folder, Long> {
    List<Folder> findAllByParentId(Long parentId);
}
