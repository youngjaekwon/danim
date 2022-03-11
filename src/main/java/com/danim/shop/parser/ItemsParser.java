package com.danim.shop.parser;

import com.danim.shop.beans.Items;
import com.danim.shop.beans.ItemsDTO;
import org.springframework.stereotype.Service;

// item 관련 파서
@Service
public class ItemsParser {
    public Items parseItems(ItemsDTO itemsDTO){
        // 파싱 정보를 저장할 아이템객체 생성
        Items item = new Items();

        /////////////// 아이템 객체에 등록될 정보 ////////////////////////////
        // 제품 번호
        item.setItemnum(itemsDTO.getItemnum());
        // 카테고리
        item.setCategory(itemsDTO.getCategory());
        // 제조사
        item.setMfr(itemsDTO.getMfr());
        // 제품명
        item.setName(itemsDTO.getName());
        // 제품 정보
        item.setInfo(itemsDTO.getInfo());
        // 가격
        item.setPrice(itemsDTO.getPrice() + "");
        // 사진
        item.setPic(itemsDTO.getPic());
        // 재고
        item.setStock(itemsDTO.getStock());
        ///////////////////////////////////////////////////////////////


        return item;
    }

    public ItemsDTO parseItems(Items item){
        // 파싱 정보를 저장할 아이템 DTO 객체 생성
        ItemsDTO itemsDTO = new ItemsDTO();

        /////////////// 아이템 DTO 객체에 등록될 정보 ////////////////////////////
        // 제품 번호
        itemsDTO.setItemnum(item.getItemnum());
        // 카테고리
        itemsDTO.setCategory(item.getCategory());
        // 제조사
        itemsDTO.setMfr(item.getMfr());
        // 제품명
        itemsDTO.setName(item.getName());
        // 제품 정보
        itemsDTO.setInfo(item.getInfo());
        // 가격
        itemsDTO.setPrice(Integer.parseInt(item.getPrice()));
        // 사진
        itemsDTO.setPic(item.getPic());
        // 재고
        itemsDTO.setStock(item.getStock());
        ///////////////////////////////////////////////////////////////


        return itemsDTO;
    }
}
