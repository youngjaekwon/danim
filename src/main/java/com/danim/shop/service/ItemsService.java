package com.danim.shop.service;

import com.danim.member.beans.Member;
import com.danim.member.dao.MemberDao;
import com.danim.shop.beans.Items;
import com.danim.shop.beans.ItemsDTO;
import com.danim.shop.dao.ItemsDao;
import com.danim.shop.parser.ItemsParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemsService {
    private final ItemsDao itemsDao;
    private final MemberDao memberDao;
    private final ItemsParser itemsParser;
    private final JSONParser jsonParser;

    @Autowired
    public ItemsService(ItemsDao itemsDao, MemberDao memberDao, ItemsParser itemsParser, JSONParser jsonParser) {
        this.itemsDao = itemsDao;
        this.memberDao = memberDao;
        this.itemsParser = itemsParser;
        this.jsonParser = jsonParser;
    }

    // select
    public Items select(String itemnum){
        return itemsDao.select(itemnum);
    }

    // 제품 리스트
    public List<ItemsDTO> getList(String category){
        if (category == null) category = ""; // category가 null 일경우 빈 문자열로 변환
        List<Items> itemsList = itemsDao.searchAllByAtt("CATEGORY", category); // category를 통해 아이템 검색
        List<ItemsDTO> itemsDTOList = new ArrayList<>(); // 반환할 아이템DTO 리스트
        for (Items item : itemsList){
            itemsDTOList.add(itemsParser.parseItems(item)); // items to itemsDTO
        }
        if (itemsDTOList.isEmpty()) return null; // 비어있을경우 null 반환
        return itemsDTOList; // ItemsDTO list 반환
    }

    // JSONString을 ItemsDTO list로 바꾸는 함수
    public List<ItemsDTO> getItemListFromJSONString(String JSONStringItemList){
        JSONArray jsonItemList = new JSONArray();
        try {
            if (JSONStringItemList != null && !JSONStringItemList.isEmpty())
                jsonItemList = (JSONArray) jsonParser.parse(JSONStringItemList); // 전달받은 JSONString으로 된 item list JSONArray로 변환
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ItemsDTO> itemList = new ArrayList<>(); // 반환할 리스트
        // 리스트에 제품 번호, 갯수 등록
        for (Object jsonItem : jsonItemList) {
            Items item = itemsDao.select((String)((JSONObject) jsonItem).get("itemnum"));
            ItemsDTO itemsDTO = itemsParser.parseItems(item);
            try {
                itemsDTO.setQuantity((String) ((JSONObject) jsonItem).get("quantity"));
            } catch (Exception e) {
                itemsDTO.setQuantity((Long) ((JSONObject) jsonItem).get("quantity") + "");
            }

            itemList.add(itemsDTO);
        }
        if (itemList.isEmpty()) return null; // 리스트가 비었을경우 null 반환
        return itemList; // 리스트 반환
    }

    // 단일 아이템 장바구니에 추가 (로그인)
    public boolean addBasketList(String memnum, String addItem){
        Member member = memberDao.select(memnum); // 로그인된 멤버
        String jsonStringBasketList = member.getBasket(); // 저장된 장바구니 리스트
        try {
            JSONArray jsonArrayBasketList = new JSONArray(); // 기존 장바구니가 null 이면 사용할 빈 JSONArray 생성
            if (jsonStringBasketList != null)
                jsonArrayBasketList = (JSONArray) jsonParser.parse(jsonStringBasketList); // JSONString to JSONArray
            JSONObject jsonObjectAddItem = (JSONObject) jsonParser.parse(addItem); // JSONString to JSONObject
            String itemNum = (String) jsonObjectAddItem.get("itemnum"); // 추가 요청된 아이템 제품 번호
            int itemQuantity = Integer.parseInt((String) jsonObjectAddItem.get("quantity")); // 추가 요청된 아이템 수량
            int isDuplicated = 0; // 중복 여부

            // 기존 리스트 돌며 중복 확인
            for (Object item : jsonArrayBasketList) {
                // 중복 확인된 경우
                if (((JSONObject) item).get("itemnum").equals(itemNum)){
                    int currentQuantity = Integer.parseInt((String) ((JSONObject) item).get("quantity")); // 기존 수량
                    ((JSONObject)item).replace("quantity", currentQuantity + itemQuantity + ""); // 기존 수량 + 추가 수량으로 갱신
                    isDuplicated += 1; // 중복 true
                }
            }
            // 중복 false인 경우 직접 추가
            if (isDuplicated == 0) jsonArrayBasketList.add(jsonObjectAddItem); // JSONArray에 새 아이템 추가

            jsonStringBasketList = jsonArrayBasketList.toJSONString(); // JSONArray to JSONString
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memberDao.update(memnum, "BASKET", jsonStringBasketList) > 0; // 멤버 장바구니 업데이트
    }

    // 쿠키에 저장된 리스트 장바구니에 추가 (로그인)
    public boolean addBasketList(Member member, String jsonStringBasketListFromCookie){
        String jsonStringBasketList = member.getBasket(); // 멤버에 저장된 장바구니 리스트
        try {
            JSONArray jsonArrayBasketList = new JSONArray();
            if (jsonStringBasketList != null)
                jsonArrayBasketList = (JSONArray) jsonParser.parse(jsonStringBasketList); // // 멤버에 저장된 장바구니 리스트 JSONString to JSONArray
            JSONArray jsonArrayBasketListFromCookie = (JSONArray) jsonParser.parse(jsonStringBasketListFromCookie); // 쿠키에 저장된 장바구니 리스트 JSONString to JSONArray
            // 요청된 리스트 돌며 아이템들 추가
            for (Object jsonObjectAddItem : jsonArrayBasketListFromCookie) {
                String itemNum = (String) ((JSONObject)jsonObjectAddItem).get("itemnum"); // 추가 요청된 아이템 제품 번호
                int itemQuantity = Integer.parseInt((String) ((JSONObject)jsonObjectAddItem).get("quantity")); // 추가 요청된 아이템 수량
                int isDuplicated = 0; // 중복 여부

                // 기존 리스트 돌며 중복 확인
                for (Object item : jsonArrayBasketList) {
                    // 중복 확인된 경우
                    if (((JSONObject) item).get("itemnum").equals(itemNum)){
                        int currentQuantity = ((Long) ((JSONObject) item).get("quantity")).intValue(); // 기존 수량
                        ((JSONObject)item).replace("quantity", currentQuantity + itemQuantity + ""); // 기존 수량 + 추가 수량으로 갱신
                        isDuplicated += 1; // 중복 true
                    }
                }
                // 중복 false인 경우 직접 추가
                if (isDuplicated == 0) jsonArrayBasketList.add(jsonObjectAddItem); // 쿠키에 저장된 아이템들 추가
            }

            jsonStringBasketList = jsonArrayBasketList.toJSONString(); // JSONArray to JSONString
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memberDao.update(member.getMemnum(), "BASKET", jsonStringBasketList) > 0; // 멤버 장바구니 업데이트
    }

    // 장바구니에 추가 (비로그인)
    public String addBasketListNotLogin(String jsonStringBasketList, String addItem){
        try {
            JSONArray jsonArrayBasketList = new JSONArray(); // 리턴할 장바구니 리스트
            if (!jsonStringBasketList.isEmpty()) { // 기존 장바구니가 비어있지 않으면
                jsonArrayBasketList = (JSONArray) jsonParser.parse(jsonStringBasketList); // 기존 장바구니 JSONString to JSONArray
            }
            JSONObject jsonObjectAddItem = (JSONObject) jsonParser.parse(addItem); // 추가할 아이템 JSONString to JSONObject
            String itemNum = (String) jsonObjectAddItem.get("itemnum"); // 추가 요청된 아이템 제품 번호
            int itemQuantity = Integer.parseInt((String) jsonObjectAddItem.get("quantity")); // 추가 요청된 아이템 수량
            int isDuplicated = 0; // 중복 여부

            // 기존 리스트 돌며 중복 확인
            for (Object item : jsonArrayBasketList) {
                // 중복 확인된 경우
                if (((JSONObject) item).get("itemnum").equals(itemNum)){
                    int currentQuantity = Integer.parseInt((String) ((JSONObject) item).get("quantity")); // 기존 수량
                    ((JSONObject)item).replace("quantity", currentQuantity + itemQuantity + ""); // 기존 수량 + 추가 수량으로 갱신
                    isDuplicated += 1; // 중복 true
                }
            }
            // 중복 false인 경우 직접 추가
            if (isDuplicated == 0) jsonArrayBasketList.add(jsonObjectAddItem); // JSONArray에 새 아이템 추가

            jsonStringBasketList = jsonArrayBasketList.toJSONString(); // 장바구니 리스트 JSONArray to JSONString
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStringBasketList; // 장바구니 리스트 리턴
    }
}
