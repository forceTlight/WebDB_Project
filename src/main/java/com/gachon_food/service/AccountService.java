package com.gachon_food.service;

import com.gachon_food.domain.Account;
import com.gachon_food.domain.Role;
import com.gachon_food.dto.AccountDto;
import com.gachon_food.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
Security CustomMizing - UserDetailService (유저 정보)
 */
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(email)); // DB로 부터 회원 정보를 가져온다.
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(); // 권한 List를 만든다
        if("admin".equals(email)){
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue())); // 어드민 권한 부여
        }else {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue())); // 유저 권한 부여
        }
        System.out.println(new User(account.getEmail(),account.getPassword(),grantedAuthorities));
        return new User(account.getEmail(),account.getPassword(),grantedAuthorities); // Account값이 입력된 UserDetails를 리턴한다.
    }
    // Account 저장 (비밀번호 복호화됨)
    public Account save(AccountDto.AccountRequestDto accountRequestDto){
        Account account = new Account(accountRequestDto);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        account.encodePassword(passwordEncoder); // 비밀번호 복호화
        return accountRepository.save(account); // 저장
    }
    // 요청이 들어오고 account가 들어오면 account의 비밀번호를 인코딩 한 뒤에 저장한다.
}
