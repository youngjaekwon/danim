package com.danim.files.dao;

import com.danim.files.beans.FilesEntity;

import java.util.List;

public interface IFilesDao {
    int insert(FilesEntity file);
    FilesEntity select(String fnum);
    List<FilesEntity> selectFiles(String itemnum);
    int delete(String fnum);
}
