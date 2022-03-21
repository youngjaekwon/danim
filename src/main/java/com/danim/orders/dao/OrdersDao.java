package com.danim.orders.dao;

import com.danim.orders.beans.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrdersDao implements IOrdersDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrdersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 주문 생성
    @Override
    public int insert(Orders orders) {
        String SQL = "INSERT INTO ORDERS VALUES (ORDERS_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // 주문 생성 성공: 1반환, 실패: 0 반환
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, orders.getMemnum());
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(orders.getOrderdate().getTime()));
                preparedStatement.setString(3, orders.getName());
                preparedStatement.setString(4, orders.getZipcode());
                preparedStatement.setString(5, orders.getAddr());
                preparedStatement.setString(6, orders.getMobile());
                preparedStatement.setString(7, orders.getState());
                preparedStatement.setString(8, orders.getWaybillnum());
                preparedStatement.setString(9, orders.getPrice());
                preparedStatement.setString(10, orders.getPayment());
                preparedStatement.setString(11, orders.getRequest());
                preparedStatement.setString(12, orders.getItemlist());
                preparedStatement.setString(13, orders.getQna());
            }
        });
    }

    // 셀렉트
    @Override
    public Orders select(int ordernum) {
        List<Orders> orders = null;
        String SQL = "SELECT * FROM ORDERS WHERE ORDERNUM = ?";
        orders = jdbcTemplate.query(SQL, new RowMapper<Orders>() {
            @Override
            public Orders mapRow(ResultSet resultSet, int i) throws SQLException {
                Orders order = new Orders();
                order.setOrdernum(resultSet.getInt("ORDERNUM"));
                order.setMemnum(resultSet.getString("MEMNUM"));
                order.setOrderdate(resultSet.getTimestamp("ORDERDATE"));
                order.setName(resultSet.getString("NAME"));
                order.setZipcode(resultSet.getString("ZIPCODE"));
                order.setAddr(resultSet.getString("ADDR"));
                order.setMobile(resultSet.getString("MOBILE"));
                order.setState(resultSet.getString("STATE"));
                order.setWaybillnum(resultSet.getString("WAYBILLNUM"));
                order.setPrice(resultSet.getString("PRICE"));
                order.setPayment(resultSet.getString("PAYMENT"));
                order.setRequest(resultSet.getString("REQUEST"));
                order.setItemlist(resultSet.getString("ITEMSLIST"));
                order.setQna(resultSet.getString("QNA"));
                return order;
            }
        }, ordernum);

        if (orders.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return orders.get(0);
    }

    // attribute, data를 이용한 조회 (keyword가 String형 일때)
    @Override
    public Orders search(String attribute, String keyword) {
        List<Orders> orders = null;
        String SQL = "SELECT * FROM ORDERS WHERE " + attribute + " = ?";
        orders = jdbcTemplate.query(SQL, new RowMapper<Orders>() {
            @Override
            public Orders mapRow(ResultSet resultSet, int i) throws SQLException {
                Orders order = new Orders();
                order.setOrdernum(resultSet.getInt("ORDERNUM"));
                order.setMemnum(resultSet.getString("MEMNUM"));
                order.setOrderdate(resultSet.getTimestamp("ORDERDATE"));
                order.setName(resultSet.getString("NAME"));
                order.setZipcode(resultSet.getString("ZIPCODE"));
                order.setAddr(resultSet.getString("ADDR"));
                order.setMobile(resultSet.getString("MOBILE"));
                order.setState(resultSet.getString("STATE"));
                order.setWaybillnum(resultSet.getString("WAYBILLNUM"));
                order.setPrice(resultSet.getString("PRICE"));
                order.setPayment(resultSet.getString("PAYMENT"));
                order.setRequest(resultSet.getString("REQUEST"));
                order.setItemlist(resultSet.getString("ITEMSLIST"));
                order.setQna(resultSet.getString("QNA"));
                return order;
            }
        }, keyword);

        if (orders.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return orders.get(0);
    }

    // attribute, data를 이용한 조회 (keyword가 int 형일때)
    @Override
    public Orders search(String attribute, int keyword) {
        List<Orders> orders = null;
        String SQL = "SELECT * FROM ORDERS WHERE " + attribute + " = ?";
        orders = jdbcTemplate.query(SQL, new RowMapper<Orders>() {
            @Override
            public Orders mapRow(ResultSet resultSet, int i) throws SQLException {
                Orders order = new Orders();
                order.setOrdernum(resultSet.getInt("ORDERNUM"));
                order.setMemnum(resultSet.getString("MEMNUM"));
                order.setOrderdate(resultSet.getTimestamp("ORDERDATE"));
                order.setName(resultSet.getString("NAME"));
                order.setZipcode(resultSet.getString("ZIPCODE"));
                order.setAddr(resultSet.getString("ADDR"));
                order.setMobile(resultSet.getString("MOBILE"));
                order.setState(resultSet.getString("STATE"));
                order.setWaybillnum(resultSet.getString("WAYBILLNUM"));
                order.setPrice(resultSet.getString("PRICE"));
                order.setPayment(resultSet.getString("PAYMENT"));
                order.setRequest(resultSet.getString("REQUEST"));
                order.setItemlist(resultSet.getString("ITEMSLIST"));
                order.setQna(resultSet.getString("QNA"));
                return order;
            }
        }, keyword);

        if (orders.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return orders.get(0);
    }

    // 업데이트
    @Override
    public int update(int ordernum, String attribute, String revisedData) {
        String SQL = "UPDATE ORDERS SET " + attribute + " = ? WHERE ORDERNUM = ?";
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, revisedData);
                preparedStatement.setInt(2, ordernum);
            }
        });
    }

    // 주문 삭제
    @Override
    public int delete(int ordernum) {
        String SQL = "DELETE FROM ORDERS WHERE ORDERNUM = ?";
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, ordernum);
            }
        });
    }

    @Override
    public List<Orders> selectAll() {
        List<Orders> orders = null;
        String SQL = "SELECT * FROM ORDERS";
        orders = jdbcTemplate.query(SQL, new RowMapper<Orders>() {
            @Override
            public Orders mapRow(ResultSet resultSet, int i) throws SQLException {
                Orders order = new Orders();
                order.setOrdernum(resultSet.getInt("ORDERNUM"));
                order.setMemnum(resultSet.getString("MEMNUM"));
                order.setOrderdate(resultSet.getTimestamp("ORDERDATE"));
                order.setName(resultSet.getString("NAME"));
                order.setZipcode(resultSet.getString("ZIPCODE"));
                order.setAddr(resultSet.getString("ADDR"));
                order.setMobile(resultSet.getString("MOBILE"));
                order.setState(resultSet.getString("STATE"));
                order.setWaybillnum(resultSet.getString("WAYBILLNUM"));
                order.setPrice(resultSet.getString("PRICE"));
                order.setPayment(resultSet.getString("PAYMENT"));
                order.setRequest(resultSet.getString("REQUEST"));
                order.setItemlist(resultSet.getString("ITEMSLIST"));
                order.setQna(resultSet.getString("QNA"));
                return order;
            }
        });

        if (orders.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return orders;
    }

    @Override
    public List<Orders> searchAllByFilters(String state, String qna, String sorting) {
        List<Orders> orders = null;
        String SQL = "SELECT * FROM ORDERS WHERE STATE LIKE ? AND QNA LIKE ? ORDER BY " + sorting;
        orders = jdbcTemplate.query(SQL, new RowMapper<Orders>() {
            @Override
            public Orders mapRow(ResultSet resultSet, int i) throws SQLException {
                Orders order = new Orders();
                order.setOrdernum(resultSet.getInt("ORDERNUM"));
                order.setMemnum(resultSet.getString("MEMNUM"));
                order.setOrderdate(resultSet.getTimestamp("ORDERDATE"));
                order.setName(resultSet.getString("NAME"));
                order.setZipcode(resultSet.getString("ZIPCODE"));
                order.setAddr(resultSet.getString("ADDR"));
                order.setMobile(resultSet.getString("MOBILE"));
                order.setState(resultSet.getString("STATE"));
                order.setWaybillnum(resultSet.getString("WAYBILLNUM"));
                order.setPrice(resultSet.getString("PRICE"));
                order.setPayment(resultSet.getString("PAYMENT"));
                order.setRequest(resultSet.getString("REQUEST"));
                order.setItemlist(resultSet.getString("ITEMSLIST"));
                order.setQna(resultSet.getString("QNA"));
                return order;
            }
        }, state, qna);

        if (orders.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return orders;
    }
}
