package com.hy.shop.member.model.service;

import com.hy.shop.member.model.dao.MemberMapper;
import com.hy.shop.member.model.vo.Account;
import com.hy.shop.commom.util.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final MemberMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = userMapper.findUser(username);

        if (account == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(account.getId(), account.getPasswd(), authorities);
    }

    @Transactional
    public boolean join(String userId, String userPwd) {
        Account checkUser = new Account();
        checkUser.setId(userId);

        if (userMapper.findUser(String.valueOf(checkUser)) != null) {
            return false;
        }

        Account newUser = new Account();
        newUser.setId(userId);

        // SHA256으로 비밀번호 해싱
        String salt = SHA256.generateSalt();
        String hashedPassword = SHA256.getEncrypt(userPwd, salt.getBytes());

        newUser.setPasswd(hashedPassword);

        userMapper.save(newUser);
        return true;
    }
}
