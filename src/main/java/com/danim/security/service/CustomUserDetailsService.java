package com.danim.security.service;

import com.danim.member.beans.Member;
import com.danim.member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private MemberDao memberDao;

    @Autowired
    public CustomUserDetailsService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberDao.select(username);
        GrantedAuthority authority = new SimpleGrantedAuthority(member.getRole());
        UserDetails userDetails = (UserDetails)new User(member.getEmail(),
                member.getPwd(), Arrays.asList(authority));
        return userDetails;
    }
}
