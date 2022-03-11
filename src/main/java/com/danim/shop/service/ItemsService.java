package com.danim.shop.service;

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
    private final ItemsParser itemsParser;
    private final JSONParser jsonParser;

    @Autowired
    public ItemsService(ItemsDao itemsDao, ItemsParser itemsParser, JSONParser jsonParser) {
        this.itemsDao = itemsDao;
        this.itemsParser = itemsParser;
        this.jsonParser = jsonParser;
    }

    // select
    public Items select(String itemnum){
        return itemsDao.select(itemnum);
    }

    public List<ItemsDTO> getCheckoutList(String JSONStringItemList) throws ParseException {
        JSONArray JSONItemList = (JSONArray) jsonParser.parse(JSONStringItemList); //
        List<ItemsDTO> itemList = new ArrayList<>();
        for (Object JSONItem : JSONItemList) {
            Items item = itemsDao.select((String)((JSONObject) JSONItem).get("itemnum"));
            ItemsDTO itemsDTO = itemsParser.parseItems(item);
            itemsDTO.setQuantity((String)((JSONObject) JSONItem).get("quantity"));
            itemList.add(itemsDTO);
        }
        if (itemList.isEmpty()) return null;
        return itemList;
    }
}
