package com.danim.member.parser;

import com.danim.member.beans.Member;
import com.danim.member.dto.MemberDTO;
import org.springframework.stereotype.Service;

// 멤버관련 파서
@Service
public class MemberParser {
    public Member parseMember(MemberDTO dto){
        // 파싱 정보를 저장할 멤버객체 생성
        Member member = new Member();

        /////////////// 멤버 객체에 등록될 정보 ////////////////////////////
        // 이메일: email@email2
        member.setEmail(dto.getEmail1() + "@" + dto.getEmail2());
        // 비밀번호
        member.setPwd(dto.getPwd());
        // 이름
        member.setName(dto.getName());
        // 닉네임
        member.setNickname(dto.getNickname());
        // 우편번호
        member.setZipcode(dto.getZipcode());
        // 주소: addr, addrDetail
        member.setAddr(dto.getAddr() + ", " + dto.getAddrDetail());
        // 전화번호: signupMobile1-signupMobile2-signupMobile3
        member.setMobile(dto.getMobile1() + "-"
                + dto.getMobile2() + "-" + dto.getMobile3());
        ///////////////////////////////////////////////////////////////

        return member;
    }

    public MemberDTO parseMember(Member member){
        // 파싱 정보를 저장할 멤버DTO객체 생성
        MemberDTO memberDTO = new MemberDTO();

        /////////////// 멤버DTO 객체에 등록될 정보 ////////////////////////////
        // 이메일 전체
        memberDTO.setEmail(member.getEmail());
        // 이메일: email@email2
        if (member.getEmail() != null){
            String[] email = member.getEmail().split("@");
            memberDTO.setEmail1(email[0]);
            memberDTO.setEmail2(email[1]);
        }
        // 비밀번호
        memberDTO.setPwd(member.getPwd());
        // 이름
        memberDTO.setName(member.getName());
        // 닉네임
        memberDTO.setNickname(member.getNickname());
        // 우편번호
        memberDTO.setZipcode(member.getZipcode());
        // 주소: addr, addrDetail
        if (member.getAddr() != null){
            String[] addr = member.getAddr().split(",");
            memberDTO.setAddr(addr[0]);
            memberDTO.setAddrDetail(addr[1].trim());
        }
        // 전화번호: signupMobile1-signupMobile2-signupMobile3
        if (member.getMobile() != null){
            String[] mobile = member.getMobile().split("-");
            memberDTO.setMobile1(mobile[0]);
            memberDTO.setMobile2(mobile[1]);
            memberDTO.setMobile3(mobile[2]);
        }
        ///////////////////////////////////////////////////////////////

        return memberDTO;
    }
}
