package com.danim.items.controller;

import com.danim.files.beans.FilesEntity;
import com.danim.files.service.FilesService;
import com.danim.files.util.MultipartFileUploadProcessor;
import com.danim.items.beans.ItemsDTO;
import com.danim.items.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/items")
public class ItemsController {
    private final FilesService filesService;
    private final ItemsService itemsService;

    @Autowired
    public ItemsController(FilesService filesService, ItemsService itemsService) {
        this.filesService = filesService;
        this.itemsService = itemsService;
    }

    @RequestMapping(value = "/doReg", method = RequestMethod.POST)
    public String doReg(ItemsDTO itemsDTO, MultipartRequest multipartRequest) throws IOException {
        // multipart request로 넘어온 파일 등록 및 파싱
        Map<String, Object> filesMap = MultipartFileUploadProcessor.parsFiles(multipartRequest);

        // DB에 추가할 파일 리스트
        List<FilesEntity> uploadedFiles = (List<FilesEntity>) filesMap.get("filesEntityList");
        // itemsDTO에 저장할 사진 리스트
        String pics = (String) filesMap.get("storedFileNames");

        // itemsDTO에 사진 리스트 저장
        itemsDTO.setPic(pics);
        // item 등록 및 등록된 item 번호 가져옴
        String itemnum = itemsService.regItem(itemsDTO);

        // DB에 파일 추가
        if (uploadedFiles != null) uploadedFiles.forEach(filesEntity -> {
            filesEntity.setItemnum(itemnum);
            filesService.regFile(filesEntity);
        });

        return "redirect:/admin/items";
    }
}
