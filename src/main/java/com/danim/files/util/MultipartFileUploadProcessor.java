package com.danim.files.util;

import com.danim.files.beans.FilesEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MultipartFileUploadProcessor {

    public static final String FilePath = "/Users/yjkwon/Desktop/java-project/danim/src/main/webapp/resources/upload";

    public static Map<String, Object> parsFiles(MultipartHttpServletRequest multipartRequest) throws IOException {
        if (multipartRequest == null || multipartRequest.getFileMap().isEmpty()) return null;

        // 멀티파일 리스트
        List<MultipartFile> multiFiles = multipartRequest.getFiles("files");
        if (multiFiles.isEmpty()) return null;

        // 리턴할 Files Entity 리스트
        List<FilesEntity> filesEntityList = new ArrayList<>();
        // 리턴할 저장된 파일 이름
        String storedFileNames = "";
        // 리턴할 맵
        Map<String, Object> returnMap = new HashMap<>();

        for (MultipartFile file : multiFiles){
            /*
            * 1. MultipartRequest 로 넘어온 파일이 없을때
            * 2. application/octet-stream 라는 contentType을 가진 MultipartFile 이 넘어옴
            * 3. 해당 MultipartFile 이 있으면 return null
            * */
            if (file.getContentType().equals("application/octet-stream")) return null;
            // 파일명
            String originalFilename = file.getOriginalFilename();
            // 파일 확장자
            String originalFileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 랜덤한 파일명 생성 (저장될 파일 이름)
            // UUID클래스 - (특수문자를 포함한)문자를 랜덤으로 생성
            // -는 모두 빈문자열로 치환
            String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;
            // 리턴할 파일 이름을 구분자와 함게 저장
            storedFileNames += storedFileName + "$";

            // 저장할 파일객체 생성
            File storedFile = new File(FilePath + File.separator + storedFileName);
            FilesEntity filesEntity = new FilesEntity();

            // File Entity 필드 추가
            filesEntity.setOriginalFilename(originalFilename);
            filesEntity.setStoredFileName(storedFileName);
            filesEntity.setFtype(file.getContentType());
            filesEntity.setFsize(file.getSize() + "");

            // 파일 저장
            file.transferTo(storedFile);
            filesEntityList.add(filesEntity);
        }

        storedFileNames = storedFileNames.substring(0, storedFileNames.length() - 1);

        returnMap.put("filesEntityList", filesEntityList);
        returnMap.put("storedFileNames", storedFileNames);

        return returnMap;
    }

    public static boolean delFiles(List<FilesEntity> files) {
        if (files == null || files.isEmpty()) return true;

        for (FilesEntity file : files) {
            File targetFile = new File(FilePath + File.separator + file.getStoredFileName());
            if (!targetFile.delete()) return false;
        }

        return true;
    }
}
