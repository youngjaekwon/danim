package com.danim.orders.dao;

import com.danim.member.beans.Member;
import com.danim.orders.beans.Orders;

import java.util.List;

public interface IOrdersDao {
    public int insert(Orders orders);
    public Orders select(int ordernum);
    public Orders search(String attribute, String keyword);
    public Orders search(String attribute, int keyword);
    public int update(int ordernum, String attribute, String revisedData);
    public int delete(int ordernum);
    public List<Orders> selectAll();
    public List<Orders> searchAllByFilters(String state, String qna, String sorting);
}
