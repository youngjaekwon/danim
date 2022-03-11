package com.danim.shop.service;

import com.danim.shop.beans.Items;
import com.danim.shop.dao.ItemsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemsService {
    private final ItemsDao itemsDao;

    @Autowired
    public ItemsService(ItemsDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    // select
    public Items select(String itemnum){
        return itemsDao.select(itemnum);
    }
}
