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

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("process");
        Account account = accountRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(email));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if("admin".equals(email)){
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }else {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }
        System.out.println(new User(account.getEmail(),account.getPassword(),grantedAuthorities));
        return new User(account.getEmail(),account.getPassword(),grantedAuthorities);
    } // UserDetails를 구현한 객체인 User 리턴
    public Account save(AccountDto.AccountRequestDto accountRequestDto){
        Account account = new Account(accountRequestDto);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        account.encodePassword(passwordEncoder);
        return accountRepository.save(account);
    }
    // 요청이 들어오고 account가 들어오면 account의 비밀번호를 인코딩 한 뒤에 저장한다.
}
