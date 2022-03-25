package com.danim.payments.dao;

import com.danim.orders.beans.Orders;
import com.danim.payments.beans.Payments;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PaymentsDao implements IPaymentsDao{

    private final JdbcTemplate jdbcTemplate;

    public PaymentsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insert(Payments payments) {
        String SQL = "INSERT INTO PAYMENTS VALUES (LPAD(PAYMENTS_SEQ.NEXTVAL, 7, 0), ?, ?, ?, ?, ?, ?)";
        // 주문 생성 성공: 1반환, 실패: 0 반환
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, payments.getOrdernum());
                preparedStatement.setString(2, payments.getImpuid());
                preparedStatement.setString(3, payments.getMerchantuid());
                preparedStatement.setString(4, payments.getPaidamount());
                preparedStatement.setString(5, payments.getApplynum());
                preparedStatement.setString(6, payments.getState());
            }
        });
    }

    @Override
    public Payments select(String paymentsnum) {
        List<Payments> payments = null;
        String SQL = "SELECT * FROM PAYMENTS WHERE PAYMENTSNUM = ?";
        payments = jdbcTemplate.query(SQL, new RowMapper<Payments>() {
            @Override
            public Payments mapRow(ResultSet resultSet, int i) throws SQLException {
                Payments payment = new Payments();
                payment.setPaymentsnum(resultSet.getString("PAYMENTSNUM"));
                payment.setOrdernum(resultSet.getString("ORDERNUM"));
                payment.setImpuid(resultSet.getString("IMPUID"));
                payment.setMerchantuid(resultSet.getString("MERCHANTUID"));
                payment.setPaidamount(resultSet.getString("PAIDAMOUNT"));
                payment.setApplynum(resultSet.getString("APPLYNUM"));
                payment.setState(resultSet.getString("STATE"));
                return payment;
            }
        }, paymentsnum);

        if (payments.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return payments.get(0);
    }

    @Override
    public Payments search(String paymentsnum, String attribute, String keyword) {
        List<Payments> payments = null;
        String SQL = "SELECT * FROM PAYMENTS WHERE PAYMENTSNUM = ? AND " + attribute + " = ?";
        payments = jdbcTemplate.query(SQL, new RowMapper<Payments>() {
            @Override
            public Payments mapRow(ResultSet resultSet, int i) throws SQLException {
                Payments payment = new Payments();
                payment.setPaymentsnum(resultSet.getString("PAYMENTSNUM"));
                payment.setOrdernum(resultSet.getString("ORDERNUM"));
                payment.setImpuid(resultSet.getString("IMPUID"));
                payment.setMerchantuid(resultSet.getString("MERCHANTUID"));
                payment.setPaidamount(resultSet.getString("PAIDAMOUNT"));
                payment.setApplynum(resultSet.getString("APPLYNUM"));
                payment.setState(resultSet.getString("STATE"));
                return payment;
            }
        }, paymentsnum, keyword);

        if (payments.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return payments.get(0);
    }
}
