package com.danim.items.service;

import com.danim.items.beans.Items;
import com.danim.items.beans.ItemsDTO;
import com.danim.items.dao.ItemsDao;
import com.danim.items.parser.ItemsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemsService {
    private final ItemsDao itemsDao;
    private final ItemsParser itemsParser;

    @Autowired
    public ItemsService(ItemsDao itemsDao, ItemsParser itemsParser) {
        this.itemsDao = itemsDao;
        this.itemsParser = itemsParser;
    }

    public String regItem(ItemsDTO itemsDTO){
        Items item = itemsParser.parseItems(itemsDTO);
        return itemsDao.insert(item);
    }
}
