package com.gachon_food.service;

import com.gachon_food.domain.Account;
import com.gachon_food.repository.AccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private AccountRepository accountRepository;

    // return null이면 로그인 실패
    public Account login(String email, String password){
        Account account = accountRepository.findByEmail(email).get();
        if(account != null){
            // 비밀번호 복호화해서 비교
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(passwordEncoder.matches(password, account.getPassword())){
                return account;
            }
        }
        // 비밀번호가 틀리거나 존재하지 않는 회원이면 null값 반환
        return null;
    }
}
