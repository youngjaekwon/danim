package com.danim.orders.dao;

import com.danim.member.beans.Member;
import com.danim.orders.beans.Orders;

import java.util.List;

public interface IOrdersDao {
    public int insert(Orders orders);
    public Orders select(String ordernum);
    public Orders search(String attribute, String keyword);
    public int update(String ordernum, String attribute, String revisedData);
    public int delete(String ordernum);
    public List<Orders> selectAll();
    public List<Orders> searchAllByFilters(String state, String qna, String sorting, String keyword);
    public List<Orders> selectAllByAtt(String attribute, String keyword);
}
