package com.danim.items.dao;

import com.danim.items.beans.Items;

import java.util.List;

public interface IItemsDao {
    public String insert(Items item);
    public Items select(String itemnum);
    public List<Items> search(String keyword);
    public Items searchByAtt(String attribute, String keyword);
    public List<Items> searchAllByAtt(String attribute, String keyword);
    public List<Items> searchAllByFilters(String category, String stock, String sorting, String keyword);
    public int update(String itemnum, String attribute, String revisedData);
    public int delete(String itemnum);
    public List<Items> selectAll();
}
