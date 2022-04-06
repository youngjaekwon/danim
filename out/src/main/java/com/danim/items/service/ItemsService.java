package com.danim.items.service;

import com.danim.files.beans.FilesEntity;
import com.danim.files.service.FilesService;
import com.danim.files.util.MultipartFileUploadProcessor;
import com.danim.items.beans.Items;
import com.danim.items.beans.ItemsDTO;
import com.danim.items.dao.ItemsDao;
import com.danim.items.parser.ItemsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ItemsService {
    private final ItemsDao itemsDao;
    private final ItemsParser itemsParser;
    private final FilesService filesService;

    @Autowired
    public ItemsService(ItemsDao itemsDao, ItemsParser itemsParser, FilesService filesService) {
        this.itemsDao = itemsDao;
        this.itemsParser = itemsParser;
        this.filesService = filesService;
    }

    // select
    public Items select(String itemnum){
        return itemsDao.select(itemnum);
    }

    // select
    public ItemsDTO getDTO(String itemnum){
        return itemsParser.parseItems(itemsDao.select(itemnum));
    }

    // 아이템 등록
    public boolean regItem(ItemsDTO itemsDTO, MultipartHttpServletRequest multipartRequest) throws IOException {

        // multipart request로 넘어온 파일 등록 및 파싱
        Map<String, Object> filesMap = MultipartFileUploadProcessor.parsFiles(multipartRequest);

        // DB에 추가할 파일 리스트
        List<FilesEntity> uploadedFiles = null;

        if (filesMap != null) {
            // DB에 추가할 파일 리스트 갱신
            uploadedFiles = (List<FilesEntity>) filesMap.get("filesEntityList");
            // itemsDTO에 저장할 사진 리스트
            String pics = (String) filesMap.get("storedFileNames");

            // itemsDTO에 사진 리스트 저장
            itemsDTO.setPic(pics);
        }

        // item 등록 및 등록된 item 번호 가져옴
        Items item = itemsParser.parseItems(itemsDTO);
        String itemnum = itemsDao.insert(item);
        if (itemnum == null || itemnum.isEmpty()) return false;

        // DB에 파일 추가
        filesService.regFiles("ITEMNUM", itemnum, uploadedFiles);

        return true;
    }

    public boolean updateItem(ItemsDTO itemsDTO, String localPics, String externalPics, MultipartHttpServletRequest multipartRequest) throws IOException {
        // 상품 번호
        String itemnum = itemsDTO.getItemnum();
        if (itemnum == null) return false;

        // multipart request로 넘어온 파일 등록 및 파싱
        Map<String, Object> filesMap = MultipartFileUploadProcessor.parsFiles(multipartRequest);

        // DB에 추가할 파일 리스트
        List<FilesEntity> uploadedFiles = null;
        // itemsDTO에 저장할 사진 리스트
        String pics = "";

        if (filesMap != null){
            // DB에 추가할 파일 리스트 갱신
            uploadedFiles = (List<FilesEntity>) filesMap.get("filesEntityList");
            // itemsDTO에 저장할 사진 리스트 갱신
            pics = (String) filesMap.get("storedFileNames");
        }

        // DB에 파일 추가
        filesService.regFiles("ITEMNUM", itemnum, uploadedFiles);

        // 들어온 local pic, external pic 추가
        if (localPics != null && !localPics.isEmpty()) {
            if (!pics.isEmpty()) pics += "$" + localPics;
            else pics += localPics;
        }
        if (externalPics != null && !externalPics.isEmpty()) {
            if (!pics.isEmpty()) pics += "$" + externalPics;
            else pics += externalPics;
        }

        // local에 저장되어 있는 사진 파일들 갱신
        filesService.updateFile("ITEMNUM", itemnum, localPics);

        // itemsDTO 사진 업데이트
        itemsDTO.setPic(pics);

        // DB에서 같은 상품번호 DTO 검색
        ItemsDTO dbItem = itemsParser.parseItems(itemsDao.select(itemnum));

        // 수정된 내용 업데이트
        if (!dbItem.getCategory().equals(itemsDTO.getCategory())){
            if (!(itemsDao.update(itemnum, "CATEGORY", itemsDTO.getCategory()) > 0)) return false;
        }
        if (!dbItem.getMfr().equals(itemsDTO.getMfr())){
            if (!(itemsDao.update(itemnum, "MFR", itemsDTO.getMfr()) > 0)) return false;
        }
        if (!dbItem.getName().equals(itemsDTO.getName())){
            if (!(itemsDao.update(itemnum, "NAME", itemsDTO.getName()) > 0)) return false;
        }
        if (!dbItem.getInfo().equals(itemsDTO.getInfo())){
            if (!(itemsDao.update(itemnum, "INFO", itemsDTO.getInfo()) > 0)) return false;
        }
        if (dbItem.getPrice() != itemsDTO.getPrice()){
            if (!(itemsDao.update(itemnum, "PRICE", itemsDTO.getPrice() + "") > 0)) return false;
        }
        if (dbItem.getStock() != itemsDTO.getStock()){
            if (!(itemsDao.update(itemnum, "STOCK", itemsDTO.getStock() + "") > 0)) return false;
        }
        return itemsDao.update(itemnum, "PIC", itemsDTO.getPic()) > 0;
    }

    public boolean delItem(String itemnum){
        if(!filesService.delFile("ITEMNUM", itemnum)) return false;

        return itemsDao.delete(itemnum) > 0;
    }
}
