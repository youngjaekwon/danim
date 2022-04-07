package com.danim.qna.dao;

import com.danim.orders.beans.Orders;
import com.danim.qna.beans.QnaEntity;
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
public class QnaDao implements IQnaDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QnaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String insert(QnaEntity qna) {
        String SQL = "INSERT INTO QNA VALUES (LPAD(QNA_SEQ.nextval, 7, 0), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // 제품 등록 성공: 1반환, 실패: 0 반환
        jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, qna.getOrdernum());
                preparedStatement.setString(2, qna.getMemnum());
                preparedStatement.setString(3, qna.getCategory());
                preparedStatement.setString(4, qna.getTitle());
                preparedStatement.setString(5, qna.getTxt());
                preparedStatement.setTimestamp(6, new java.sql.Timestamp(qna.getQdate().getTime()));
                preparedStatement.setString(7, qna.getPic());
                preparedStatement.setString(8, qna.getComments());
                preparedStatement.setString(9, qna.getState());
            }
        });

        return jdbcTemplate.queryForObject("SELECT TO_CHAR(LPAD(QNA_SEQ.currval, 7, 0)) FROM DUAL", String.class);
    }

    @Override
    public QnaEntity select(String qnanum) {
        List<QnaEntity> qnas = null;
        String SQL = "SELECT * FROM QNA WHERE QNANUM = ?";
        qnas = jdbcTemplate.query(SQL, new RowMapper<QnaEntity>() {
            @Override
            public QnaEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                QnaEntity qna = new QnaEntity();
                qna.setQnanum(resultSet.getString("QNANUM"));
                qna.setOrdernum(resultSet.getString("ORDERNUM"));
                qna.setMemnum(resultSet.getString("MEMNUM"));
                qna.setCategory(resultSet.getString("CATEGORY"));
                qna.setTitle(resultSet.getString("TITLE"));
                qna.setTxt(resultSet.getString("TXT"));
                qna.setQdate(resultSet.getTimestamp("QDATE"));
                qna.setPic(resultSet.getString("PIC"));
                qna.setComments(resultSet.getString("COMMENTS"));
                qna.setState(resultSet.getString("STATE"));
                return qna;
            }
        }, qnanum);

        if (qnas.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return qnas.get(0);
    }

    @Override
    public List<QnaEntity> search(String keyword) {
        List<QnaEntity> qnas = null;
        String SQL = "SELECT * FROM QNA WHERE TITLE LIKE ? OR TXT LIKE ?";
        qnas = jdbcTemplate.query(SQL, new RowMapper<QnaEntity>() {
            @Override
            public QnaEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                QnaEntity qna = new QnaEntity();
                qna.setQnanum(resultSet.getString("QNANUM"));
                qna.setOrdernum(resultSet.getString("ORDERNUM"));
                qna.setMemnum(resultSet.getString("MEMNUM"));
                qna.setCategory(resultSet.getString("CATEGORY"));
                qna.setTitle(resultSet.getString("TITLE"));
                qna.setTxt(resultSet.getString("TXT"));
                qna.setQdate(resultSet.getTimestamp("QDATE"));
                qna.setPic(resultSet.getString("PIC"));
                qna.setComments(resultSet.getString("COMMENTS"));
                qna.setState(resultSet.getString("STATE"));
                return qna;
            }
        }, "%" + keyword + "%", "%" + keyword + "%");

        if (qnas.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return qnas;
    }

    @Override
    public QnaEntity searchByAtt(String attribute, String keyword) {
        List<QnaEntity> qnas = null;
        String SQL = "SELECT * FROM QNA WHERE " + attribute + " LIKE ?";
        qnas = jdbcTemplate.query(SQL, new RowMapper<QnaEntity>() {
            @Override
            public QnaEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                QnaEntity qna = new QnaEntity();
                qna.setQnanum(resultSet.getString("QNANUM"));
                qna.setOrdernum(resultSet.getString("ORDERNUM"));
                qna.setMemnum(resultSet.getString("MEMNUM"));
                qna.setCategory(resultSet.getString("CATEGORY"));
                qna.setTitle(resultSet.getString("TITLE"));
                qna.setTxt(resultSet.getString("TXT"));
                qna.setQdate(resultSet.getTimestamp("QDATE"));
                qna.setPic(resultSet.getString("PIC"));
                qna.setComments(resultSet.getString("COMMENTS"));
                qna.setState(resultSet.getString("STATE"));
                return qna;
            }
        }, "%" + keyword + "%");

        if (qnas.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return qnas.get(0);
    }

    @Override
    public List<QnaEntity> searchAllByAtt(String attribute, String keyword) {
        List<QnaEntity> qnas = null;
        String SQL = "SELECT * FROM QNA WHERE " + attribute + " LIKE ? ORDER BY TO_NUMBER(QNANUM) DESC";
        qnas = jdbcTemplate.query(SQL, new RowMapper<QnaEntity>() {
            @Override
            public QnaEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                QnaEntity qna = new QnaEntity();
                qna.setQnanum(resultSet.getString("QNANUM"));
                qna.setOrdernum(resultSet.getString("ORDERNUM"));
                qna.setMemnum(resultSet.getString("MEMNUM"));
                qna.setCategory(resultSet.getString("CATEGORY"));
                qna.setTitle(resultSet.getString("TITLE"));
                qna.setTxt(resultSet.getString("TXT"));
                qna.setQdate(resultSet.getTimestamp("QDATE"));
                qna.setPic(resultSet.getString("PIC"));
                qna.setComments(resultSet.getString("COMMENTS"));
                qna.setState(resultSet.getString("STATE"));
                return qna;
            }
        }, "%" + keyword + "%");

        if (qnas.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return qnas;
    }

    @Override
    public List<QnaEntity> searchAllByFilters(String category, String state, String sorting, String keyword) {
        List<QnaEntity> qnas = null;
        String SQL = "SELECT * FROM QNA WHERE (ORDERNUM = ? OR MEMNUM = ? OR TITLE LIKE ? OR TXT LIKE ?) AND " +
                "(CATEGORY LIKE ? AND STATE LIKE ?) ORDER BY " + sorting;
        qnas = jdbcTemplate.query(SQL, new RowMapper<QnaEntity>() {
            @Override
            public QnaEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                QnaEntity qna = new QnaEntity();
                qna.setQnanum(resultSet.getString("QNANUM"));
                qna.setOrdernum(resultSet.getString("ORDERNUM"));
                qna.setMemnum(resultSet.getString("MEMNUM"));
                qna.setCategory(resultSet.getString("CATEGORY"));
                qna.setTitle(resultSet.getString("TITLE"));
                qna.setTxt(resultSet.getString("TXT"));
                qna.setQdate(resultSet.getTimestamp("QDATE"));
                qna.setPic(resultSet.getString("PIC"));
                qna.setComments(resultSet.getString("COMMENTS"));
                qna.setState(resultSet.getString("STATE"));
                return qna;
            }
        }, keyword, keyword, "%" + keyword + "%", "%" + keyword + "%", category, state);

        if (qnas.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return qnas;
    }

    @Override
    public int update(String qnanum, String attribute, String revisedData) {
        String SQL = "UPDATE QNA SET " + attribute + " = ? WHERE QNANUM = ?";
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, revisedData);
                preparedStatement.setString(2, qnanum);
            }
        });
    }

    @Override
    public int delete(String qnanum) {
        String SQL = "DELETE FROM QNA WHERE QNANUM = ?";
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, qnanum);
            }
        });
    }

    @Override
    public List<QnaEntity> selectAll() {
        List<QnaEntity> qnas = null;
        String SQL = "SELECT * FROM QNA";
        qnas = jdbcTemplate.query(SQL, new RowMapper<QnaEntity>() {
            @Override
            public QnaEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                QnaEntity qna = new QnaEntity();
                qna.setQnanum(resultSet.getString("QNANUM"));
                qna.setOrdernum(resultSet.getString("ORDERNUM"));
                qna.setMemnum(resultSet.getString("MEMNUM"));
                qna.setCategory(resultSet.getString("CATEGORY"));
                qna.setTitle(resultSet.getString("TITLE"));
                qna.setTxt(resultSet.getString("TXT"));
                qna.setQdate(resultSet.getTimestamp("QDATE"));
                qna.setPic(resultSet.getString("PIC"));
                qna.setComments(resultSet.getString("COMMENTS"));
                qna.setState(resultSet.getString("STATE"));
                return qna;
            }
        });

        if (qnas.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return qnas;
    }
}
