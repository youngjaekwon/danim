package com.danim.comments.dao;

import com.danim.comments.beans.CommentsEntity;
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
public class CommentsDao implements ICommentsDao{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String insert(CommentsEntity comment) {
        String SQL = "INSERT INTO COMMENTS VALUES (LPAD(COMMENTS_SEQ.nextval, 9, 0), ?, ?, ?, ?, ?)";

        jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, comment.getMemnum());
                preparedStatement.setString(2, comment.getBoardnum());
                preparedStatement.setString(3, comment.getQnanum());
                preparedStatement.setString(4, comment.getTxt());
                preparedStatement.setTimestamp(5, new java.sql.Timestamp(comment.getCdate().getTime()));
            }
        });

        return jdbcTemplate.queryForObject("SELECT TO_CHAR(LPAD(COMMENTS_SEQ.currval, 9, 0)) FROM DUAL", String.class);
    }

    @Override
    public CommentsEntity select(String cnum) {
        List<CommentsEntity> comments = null;
        String SQL = "SELECT * FROM COMMENTS WHERE CNUM = ?";
        comments = jdbcTemplate.query(SQL, new RowMapper<CommentsEntity>() {
            @Override
            public CommentsEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                CommentsEntity comment = new CommentsEntity();
                comment.setCnum(resultSet.getString("CNUM"));
                comment.setMemnum(resultSet.getString("MEMNUM"));
                comment.setBoardnum(resultSet.getString("BOARDNUM"));
                comment.setQnanum(resultSet.getString("QNANUM"));
                comment.setTxt(resultSet.getString("TXT"));
                comment.setCdate(resultSet.getTimestamp("CDATE"));
                return comment;
            }
        }, cnum);

        if (comments.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return comments.get(0);
    }

    @Override
    public List<CommentsEntity> selectComments(String from, String num) {
        List<CommentsEntity> comments = null;
        String SQL = "SELECT * FROM COMMENTS WHERE " + from + " = ?";
        comments = jdbcTemplate.query(SQL, new RowMapper<CommentsEntity>() {
            @Override
            public CommentsEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                CommentsEntity comment = new CommentsEntity();
                comment.setCnum(resultSet.getString("CNUM"));
                comment.setMemnum(resultSet.getString("MEMNUM"));
                comment.setBoardnum(resultSet.getString("BOARDNUM"));
                comment.setQnanum(resultSet.getString("QNANUM"));
                comment.setTxt(resultSet.getString("TXT"));
                comment.setCdate(resultSet.getTimestamp("CDATE"));
                return comment;
            }
        }, num);

        if (comments.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return comments;
    }

    @Override
    public int delete(String cnum) {
        String SQL = "DELETE FROM COMMENTS WHERE CNUM = ?";
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, cnum);
            }
        });
    }

    @Override
    public int delete(String from, String num) {
        String SQL = "DELETE FROM COMMENTS WHERE " + from + " = ?";
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, num);
            }
        });
    }
}
