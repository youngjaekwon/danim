package com.danim.files.service;

import com.danim.files.beans.FilesEntity;
import com.danim.files.dao.FilesDao;
import com.danim.files.util.MultipartFileUploadProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public boolean regFiles(String from, String num, List<FilesEntity> files){
        AtomicBoolean result = new AtomicBoolean(true);
        if (files != null) files.forEach(filesEntity -> {
            if (from.equals("ITEMNUM")){
                filesEntity.setItemnum(num);
            } else if (from.equals("BOARDNUM")){
                filesEntity.setBoardnum(num);
            } else if (from.equals("QNANUM")){
                filesEntity.setQnanum(num);
            }

            if (!(filesDao.insert(filesEntity) > 0)) result.set(false);
        });

        return result.get();
    }

    public boolean delFile(String fnum){
        return filesDao.delete(fnum) > 0;
    }

    public boolean delFile(String from, String num){
        List<FilesEntity> files = getList(from, num);

        if (!MultipartFileUploadProcessor.delFiles(files)) return false;
        filesDao.delete(from, num);

        return  true;
    }

    public boolean updateFile(String from, String num, String localFiles){
        if (from == null || num == null) return false;

        // 번호를 통한 사진 파일 검색
        List<FilesEntity> picList = getList(from, num);

        /*
            1. localFiles (업데이트된 사진 파일 리스트) 와 DB 에 저장된 파일 리스트 비교
            2. localFiles 에 파일이 없으면 DB 에서 파일 삭제
         */
        if (localFiles != null && !localFiles.isEmpty()) {
            List<String> localPicList = Arrays.asList(localFiles.split("\\$"));
            picList.forEach((file) -> {
                if (!localPicList.contains(file.getStoredFileName())){
                    delFile(file.getFnum());
                }
            });
            return true;
        } else return false;
    }

    public List<FilesEntity> getList(String from, String num){
        return filesDao.selectFiles(from, num);
    }

    public FilesEntity getFile(String fnum){
        FilesEntity file = filesDao.select(fnum);

        return file;
    }
}
