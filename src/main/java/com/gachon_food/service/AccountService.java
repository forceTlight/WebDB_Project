package com.gachon_food.service;

import com.gachon_food.domain.Account;
import com.gachon_food.domain.Role;
import com.gachon_food.dto.AccountDto;
import com.gachon_food.repository.AccountRepository;
import com.gachon_food.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("role_account"));
        System.out.println(new User(account.getEmail(),account.getPassword(),grantedAuthorities));
        return new User(account.getEmail(),account.getPassword(),grantedAuthorities);
    } // UserDetails를 구현한 객체인 User 리턴
    public Account save(AccountDto.AccountRequestDto accountRequestDto){
        // 입력한 비밀번호와 비밀번호 확인이 같다면
        Role role = roleRepository.findByEmail("role_user");
        // 일반 유저 권한을 찾아서 가져옴
        Account account = new Account(accountRequestDto);

        account.encodePassword(passwordEncoder);
        account.getRoles().add(role);
        return accountRepository.save(account);
    }
    // 요청이 들어오고 account가 들어오면 account의 비밀번호를 인코딩 한 뒤에 저장한다.
}
