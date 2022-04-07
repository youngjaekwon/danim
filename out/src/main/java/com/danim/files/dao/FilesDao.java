package com.danim.files.dao;

import com.danim.files.beans.FilesEntity;
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
public class FilesDao implements IFilesDao{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insert(FilesEntity file) {
        String SQL = "INSERT INTO FILES VALUES (LPAD(FILES_SEQ.nextval, 9, 0), ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, file.getItemnum());
                preparedStatement.setString(2, file.getBoardnum());
                preparedStatement.setString(3, file.getQnanum());
                preparedStatement.setString(4, file.getOriginalFilename());
                preparedStatement.setString(5, file.getStoredFileName());
                preparedStatement.setString(6, file.getFtype());
                preparedStatement.setString(7, file.getFsize());
            }
        });
    }

    @Override
    public FilesEntity select(String fnum) {
        List<FilesEntity> files = null;
        String SQL = "SELECT * FROM FILES WHERE FNUM = ?";
        files = jdbcTemplate.query(SQL, new RowMapper<FilesEntity>() {
            @Override
            public FilesEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                FilesEntity file = new FilesEntity();
                file.setFnum(resultSet.getString("FNUM"));
                file.setItemnum(resultSet.getString("ITEMNUM"));
                file.setBoardnum(resultSet.getString("BOARDNUM"));
                file.setQnanum(resultSet.getString("QNANUM"));
                file.setOriginalFilename(resultSet.getString("ORIGINALFILENAME"));
                file.setStoredFileName(resultSet.getString("STOREDFILENAME"));
                file.setFtype(resultSet.getString("FTYPE"));
                file.setFsize(resultSet.getString("FSIZE"));
                return file;
            }
        }, fnum);

        if (files.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return files.get(0);
    }

    @Override
    public List<FilesEntity> selectFiles(String from, String num) {
        List<FilesEntity> files = null;
        String SQL = "SELECT * FROM FILES WHERE " + from + " = ?";
        files = jdbcTemplate.query(SQL, new RowMapper<FilesEntity>() {
            @Override
            public FilesEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                FilesEntity file = new FilesEntity();
                file.setFnum(resultSet.getString("FNUM"));
                file.setItemnum(resultSet.getString("ITEMNUM"));
                file.setBoardnum(resultSet.getString("BOARDNUM"));
                file.setQnanum(resultSet.getString("QNANUM"));
                file.setOriginalFilename(resultSet.getString("ORIGINALFILENAME"));
                file.setStoredFileName(resultSet.getString("STOREDFILENAME"));
                file.setFtype(resultSet.getString("FTYPE"));
                file.setFsize(resultSet.getString("FSIZE"));
                return file;
            }
        }, num);

        if (files.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return files;
    }

    @Override
    public int delete(String fnum) {
        String SQL = "DELETE FROM FILES WHERE FNUM = ?";
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, fnum);
            }
        });
    }

    @Override
    public int delete(String from, String num) {
        String SQL = "DELETE FROM FILES WHERE " + from + " = ?";
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, num);
            }
        });
    }
}
