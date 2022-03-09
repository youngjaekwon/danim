package com.danim.member.dao;

import com.danim.member.beans.Member;

import java.util.List;

public interface IMemberDao {
    public int insert(Member member);
    public Member select(String memnum);
    public Member search(String attribute, String keyword);
    public int update(String memnum, String attribute, String revisedData);
    public int delete(String memnum);
    public List<Member> selectAll();
}
