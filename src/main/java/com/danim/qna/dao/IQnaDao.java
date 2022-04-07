package com.danim.qna.dao;

import com.danim.qna.beans.QnaEntity;

import java.util.List;

public interface IQnaDao {
    String insert(QnaEntity qna);
    QnaEntity select(String qnanum);
    List<QnaEntity> search(String keyword);
    QnaEntity searchByAtt(String attribute, String keyword);
    List<QnaEntity> searchAllByAtt(String attribute, String keyword);
    List<QnaEntity> searchAllByFilters(String category, String state, String sorting, String keyword);
    int update(String qnanum, String attribute, String revisedData);
    int delete(String qnanum);
    List<QnaEntity> selectAll();
}
