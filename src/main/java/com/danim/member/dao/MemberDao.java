package com.danim.member.dao;

import com.danim.member.beans.Member;
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
public class MemberDao implements IMemberDao{


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 회원가입
    @Override
    public int insert(Member member) {
        String SQL = "INSERT INTO MEMBER VALUES (MEMBER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, null, null, ?)";
        // 회원가입 성공: 1반환, 실패: 0 반환
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, member.getEmail());
                preparedStatement.setString(2, member.getPwd());
                preparedStatement.setString(3, member.getName());
                preparedStatement.setString(4, member.getNickname());
                preparedStatement.setString(5, member.getZipcode());
                preparedStatement.setString(6, member.getAddr());
                preparedStatement.setString(7, member.getMobile());
                preparedStatement.setInt(8, member.getIsAdmin());
            }
        });
    }

    // 셀렉트
    @Override
    public Member select(String memnum) {
        List<Member> members = null;
        String SQL = "SELECT * FROM MEMBER WHERE MEMNUM = ?";
        members = jdbcTemplate.query(SQL, new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet resultSet, int i) throws SQLException {
                Member member = new Member();
                member.setMemnum(resultSet.getString("MEMNUM"));
                member.setEmail(resultSet.getString("EMAIL"));
                member.setPwd(resultSet.getString("PWD"));
                member.setName(resultSet.getString("NAME"));
                member.setNickname(resultSet.getString("NICKNAME"));
                member.setZipcode(resultSet.getString("ZIPCODE"));
                member.setAddr(resultSet.getString("ADDR"));
                member.setMobile(resultSet.getString("MOBILE"));
                member.setBasket(resultSet.getString("BASKET"));
                member.setWishlist(resultSet.getString("WISHLIST"));
                member.setIsAdmin(resultSet.getInt("ISADMIN"));
                return member;
            }
        }, memnum);

        if (members.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return members.get(0);
    }

    // attribute, data를 이용한 조회
    @Override
    public Member search(String attribute, String keyword) {
        List<Member> members = null;
        String SQL = "SELECT * FROM MEMBER WHERE " + attribute + " = ?";
        members = jdbcTemplate.query(SQL, new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet resultSet, int i) throws SQLException {
                Member member = new Member();
                member.setMemnum(resultSet.getString("MEMNUM"));
                member.setEmail(resultSet.getString("EMAIL"));
                member.setPwd(resultSet.getString("PWD"));
                member.setName(resultSet.getString("NAME"));
                member.setNickname(resultSet.getString("NICKNAME"));
                member.setZipcode(resultSet.getString("ZIPCODE"));
                member.setAddr(resultSet.getString("ADDR"));
                member.setMobile(resultSet.getString("MOBILE"));
                member.setBasket(resultSet.getString("BASKET"));
                member.setWishlist(resultSet.getString("WISHLIST"));
                member.setIsAdmin(resultSet.getInt("ISADMIN"));
                return member;
            }
        }, keyword);

        if (members.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return members.get(0);
    }

    // 업데이트
    @Override
    public int update(String memnum, String attribute, String revisedData) {
        String SQL = "UPDATE MEMBER SET " + attribute + " = ? WHERE MEMNUM = ?";
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, revisedData);
                preparedStatement.setString(2, memnum);
            }
        });
    }

    // 회원 탈퇴
    @Override
    public int delete(String memnum) {
        String SQL = "DELETE FROM MEMBER WHERE MEMNUM = ?";
        return jdbcTemplate.update(SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, memnum);
            }
        });
    }

    // 전체 조회
    @Override
    public List<Member> selectAll() {
        List<Member> members = null;
        String SQL = "SELECT * FROM MEMBER";
        members = jdbcTemplate.query(SQL, new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet resultSet, int i) throws SQLException {
                Member member = new Member();
                member.setMemnum(resultSet.getString("MEMNUM"));
                member.setEmail(resultSet.getString("EMAIL"));
                member.setPwd(resultSet.getString("PWD"));
                member.setName(resultSet.getString("NAME"));
                member.setNickname(resultSet.getString("NICKNAME"));
                member.setZipcode(resultSet.getString("ZIPCODE"));
                member.setAddr(resultSet.getString("ADDR"));
                member.setMobile(resultSet.getString("MOBILE"));
                member.setBasket(resultSet.getString("BASKET"));
                member.setWishlist(resultSet.getString("WISHLIST"));
                member.setIsAdmin(resultSet.getInt("ISADMIN"));
                return member;
            }
        });

        if (members.isEmpty()) return null; // 조회된게 없는경우 null 반환
        return members;
    }
}
