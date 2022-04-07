package com.danim.items.dao;

import com.danim.items.beans.Items;

import java.util.List;

public interface IItemsDao {
    String insert(Items item);
    Items select(String itemnum);
    List<Items> search(String keyword);
    Items searchByAtt(String attribute, String keyword);
    List<Items> searchAllByAtt(String attribute, String keyword);
    List<Items> searchAllByFilters(String category, String stock, String sorting, String keyword);
    int update(String itemnum, String attribute, String revisedData);
    int delete(String itemnum);
    List<Items> selectAll();
}
