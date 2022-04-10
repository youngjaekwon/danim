package com.danim.files.controller;

import com.danim.files.beans.FilesEntity;
import com.danim.files.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequestMapping(value = "/files")
@Controller
public class FilesController {
    private final FilesService filesService;
    public static final String FilePath = "/Users/yjkwon/Desktop/java-project/danim/src/main/webapp/resources/upload";

    @Autowired
    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @RequestMapping(value = "/vdownload", method = RequestMethod.POST)
    public void download(@RequestParam String fnum, HttpServletResponse response) throws IOException {

        // 파일 번호를 통한 파일 검색
        FilesEntity filesEntity = filesService.getFile(fnum);

        // 파일 경로 확인
        String path = FilePath + File.separator + filesEntity.getStoredFileName();

        // 파일의 원래 이름 저장
        response.setHeader("Content-Disposition", "attachment;filename=" + filesEntity.getOriginalFilename()); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더

        FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
        OutputStream out = response.getOutputStream();

        int read = 0;
        byte[] buffer = new byte[1024];
        while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을 파일이 없음
            out.write(buffer, 0, read);
        }
    }
}
