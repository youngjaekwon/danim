package com.danim.comments.dao;

import com.danim.comments.beans.CommentsEntity;

import java.util.List;

public interface ICommentsDao {
    String insert(CommentsEntity comment);
    CommentsEntity select(String cnum);
    List<CommentsEntity> selectComments(String from, String num);
    int delete(String cnum);
    int delete(String from, String num);
}
