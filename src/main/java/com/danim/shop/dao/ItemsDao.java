package com.danim.shop.dao;

import com.danim.member.beans.Member;
import com.danim.shop.beans.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ItemsDao implements IItemsDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 제품 등록
    @Override
    public int insert(Items item) {
        String SQL = "INSERT INTO ITEMS VALUES (ITEMS_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?)";
        // 제품 등록 성공: 1반환, 실패: 0 반환
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, item.getCategory());
                preparedStatement.setString(2, item.getMfr());
                preparedStatement.setString(3, item.getName());
                preparedStatement.setString(4, item.getInfo());
                preparedStatement.setString(5, item.getPrice());
                preparedStatement.setString(6, item.getPic());
                preparedStatement.setString(7, item.getStock());
            }
        });
    }

    // 셀렉트
    @Override
    public Items select(String itemnum) {
        List<Items> items = null;
        String SQL = "SELECT * FROM ITEMS WHERE ITEMNUM = ?";
        items = jdbcTemplate.query(SQL, new RowMapper<Items>() {
            @Override
            public Items mapRow(ResultSet resultSet, int i) throws SQLException {
                Items item = new Items();
                item.setItemnum(resultSet.getString("ITEMNUM"));
                item.setCategory(resultSet.getString("CATEGORY"));
                item.setMfr(resultSet.getString("MFR"));
                item.setName(resultSet.getString("NAME"));
                item.setInfo(resultSet.getString("INFO"));
                item.setPrice(resultSet.getString("PRICE"));
                item.setPic(resultSet.getString("PIC"));
                item.setStock(resultSet.getString("STOCK"));
                return item;
            }
        }, itemnum);

        if (items.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return items.get(0);
    }

    // 제품 검색 (통합 검색)
    @Override
    public List<Items> search(String keyword) {
        List<Items> items = null;
        String SQL = "SELECT * FROM ITEMS WHERE CATEGORY LIKE ? OR MFR LIKE ? OR NAME LIKE ? OR INFO LIKE ?";
        items = jdbcTemplate.query(SQL, new RowMapper<Items>() {
            @Override
            public Items mapRow(ResultSet resultSet, int i) throws SQLException {
                Items item = new Items();
                item.setItemnum(resultSet.getString("ITEMNUM"));
                item.setCategory(resultSet.getString("CATEGORY"));
                item.setMfr(resultSet.getString("MFR"));
                item.setName(resultSet.getString("NAME"));
                item.setInfo(resultSet.getString("INFO"));
                item.setPrice(resultSet.getString("PRICE"));
                item.setPic(resultSet.getString("PIC"));
                item.setStock(resultSet.getString("STOCK"));
                return item;
            }
        }, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");

        if (items.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return items;
    }

    // 제품 검색(attribute를 이용한 부분 검색)
    @Override
    public Items searchByAtt(String attribute, String keyword) {
        List<Items> items = null;
        String SQL = "SELECT * FROM ITEMS WHERE " + attribute + " LIKE ?";
        items = jdbcTemplate.query(SQL, new RowMapper<Items>() {
            @Override
            public Items mapRow(ResultSet resultSet, int i) throws SQLException {
                Items item = new Items();
                item.setItemnum(resultSet.getString("ITEMNUM"));
                item.setCategory(resultSet.getString("CATEGORY"));
                item.setMfr(resultSet.getString("MFR"));
                item.setName(resultSet.getString("NAME"));
                item.setInfo(resultSet.getString("INFO"));
                item.setPrice(resultSet.getString("PRICE"));
                item.setPic(resultSet.getString("PIC"));
                item.setStock(resultSet.getString("STOCK"));
                return item;
            }
        }, "%" + keyword + "%");

        if (items.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return items.get(0);
    }

    // 제품 검색(attribute를 이용한 전체 리스트 검색)
    @Override
    public List<Items> searchAllByAtt(String attribute, String keyword) {
        List<Items> items = null;
        String SQL = "SELECT * FROM ITEMS WHERE " + attribute + " LIKE ? ORDER BY TO_NUMBER(ITEMNUM)";
        items = jdbcTemplate.query(SQL, new RowMapper<Items>() {
            @Override
            public Items mapRow(ResultSet resultSet, int i) throws SQLException {
                Items item = new Items();
                item.setItemnum(resultSet.getString("ITEMNUM"));
                item.setCategory(resultSet.getString("CATEGORY"));
                item.setMfr(resultSet.getString("MFR"));
                item.setName(resultSet.getString("NAME"));
                item.setInfo(resultSet.getString("INFO"));
                item.setPrice(resultSet.getString("PRICE"));
                item.setPic(resultSet.getString("PIC"));
                item.setStock(resultSet.getString("STOCK"));
                return item;
            }
        }, "%" + keyword + "%");

        if (items.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return items;
    }

    // 업데이트
    @Override
    public int update(String itemnum, String attribute, String revisedData) {
        String SQL = "UPDATE ITEMS SET " + attribute + " = ? WHERE ITEMNUM = ?";
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, revisedData);
                preparedStatement.setString(2, itemnum);
            }
        });
    }

    // 제품 삭제
    @Override
    public int delete(String itemnum) {
        String SQL = "DELETE FROM ITEMS WHERE ITEMNUM = ?";
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, itemnum);
            }
        });
    }

    // 전체 조회
    @Override
    public List<Items> selectAll() {
        List<Items> items = null;
        String SQL = "SELECT * FROM ITEMS";
        items = jdbcTemplate.query(SQL, new RowMapper<Items>() {
            @Override
            public Items mapRow(ResultSet resultSet, int i) throws SQLException {
                Items item = new Items();
                item.setItemnum(resultSet.getString("ITEMNUM"));
                item.setCategory(resultSet.getString("CATEGORY"));
                item.setMfr(resultSet.getString("MFR"));
                item.setName(resultSet.getString("NAME"));
                item.setInfo(resultSet.getString("INFO"));
                item.setPrice(resultSet.getString("PRICE"));
                item.setPic(resultSet.getString("PIC"));
                item.setStock(resultSet.getString("STOCK"));
                return item;
            }
        });

        if (items.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return items;
    }
}
