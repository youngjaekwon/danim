package com.danim.files.dao;

import com.danim.files.beans.FilesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
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
        return null;
    }

    @Override
    public List<FilesEntity> selectFiles(String itemnum) {
        return null;
    }

    @Override
    public int delete(String fnum) {
        return 0;
    }
}
