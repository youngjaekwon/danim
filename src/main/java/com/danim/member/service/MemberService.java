package com.danim.member.service;

import com.danim.member.beans.Member;
import com.danim.member.dao.MemberDao;
import com.danim.member.dto.MemberDTO;
import com.danim.member.parser.MemberParser;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class MemberService {
    private final MemberDao memberDao;

    @Autowired
    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    // 회원 가입 메소드
    public boolean signup(MemberDTO dto, HttpSession session){
        Member member = MemberParser.parseMemberDTOtoEntity(dto); // MemberDTO를 Member 객체로 변환

        // 관리자 여부: 0(일반회원) 고정
        member.setIsAdmin(0);

        // insert 등록 성공여부 리턴
        if (memberDao.insert(member) > 0) {
            String memnum = memberDao.search("EMAIL", member.getEmail()).getMemnum(); // 등록된 회원번호 반환
            session.setAttribute("user", memnum); // session의 user attribute에 memnum 추가 (로그인)
            return true; // 성공시 true 리턴
        }
        else return false ; // 실패시 false 리턴
    }

    // 이메일 중복 확인 메소드
    public boolean isDuplicatedEmail(String email){
        Member selectedMember = memberDao.search("EMAIL", email); // 이메일 이용하여 데이터베이스 조회
        return selectedMember != null; // 조회된 회원이 있으면(이메일이 중복됨) true 반환, 없으면 false 반환
    }

    // 로그인 확인 메소드
    public boolean loginCheck(Member member, HttpSession session){
        Member searchedMember = memberDao.search("EMAIL", member.getEmail()); // 로그인 시도 멤버 확인
        if (searchedMember != null && searchedMember.getPwd() != null && searchedMember.getPwd().equals(member.getPwd())){ // 비밀번호 확인
            session.setAttribute("user", searchedMember.getMemnum()); // 로그인 성공시 회원번호 세션등록
            return true;
        } else {
            return false;
        }
    }

    // select
    public Member selectMember(String memnum){
        return memberDao.select(memnum);
    }

    // 회원정보 수정
    public int modifyMember(Member member){
        int result = 0;
        result += memberDao.update(member.getMemnum(), "PWD", member.getPwd());
        result += memberDao.update(member.getMemnum(), "NICKNAME", member.getNickname());
        result += memberDao.update(member.getMemnum(), "ZIPCODE", member.getZipcode());
        result += memberDao.update(member.getMemnum(), "ADDR", member.getAddr());
        result += memberDao.update(member.getMemnum(), "MOBILE", member.getMobile());
        return result;
    }

    public boolean modify(String memnum, String attribute, String revisedData){
        return memberDao.update(memnum, attribute, revisedData) > 0;
    }

    // 비밀번호 찾기
    public String doFindPwd(String email, String mobile){
        // email로 DB 조회
        Member member = memberDao.search("EMAIL", email);
        // 조회된 멤버의 전화번호가 입력된 값과 같은지 확인
        if (member.getMobile() != null && member.getMobile().equals(mobile)){
            return member.getMemnum(); // 같으면 회원번호 반환
        } else return null; // 같지 않으면 null 반환
    }

    // 아이디 찾기
    public String doFindEmail(String name, String mobile){
        // email로 DB 조회
        Member member = memberDao.search("NAME", name);
        // 조회된 멤버의 전화번호가 입력된 값과 같은지 확인
        if (member.getMobile() != null && member.getMobile().equals(mobile)){
            return member.getEmail(); // 같으면 회원번호 반환
        } else return null; // 같지 않으면 null 반환
    }
}
