package com.danim.files.service;

import com.danim.files.beans.FilesEntity;
import com.danim.files.dao.FilesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilesService {
    private final FilesDao filesDao;

    @Autowired
    public FilesService(FilesDao filesDao) {
        this.filesDao = filesDao;
    }

    public boolean regFile(FilesEntity file){
        return filesDao.insert(file) > 0;
    }
}
