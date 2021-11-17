package com.gachon_food.validator;

import com.gachon_food.domain.Account;
import com.gachon_food.dto.LoginFormDto;
import com.gachon_food.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoginValidator implements Validator {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object obj, Errors errors) {
        LoginFormDto login = (LoginFormDto)obj;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account account = accountRepository.findByEmail(login.getEmail()).get();
        if(account == null){ // 아이디가 없다면
            errors.rejectValue("email", "key", "아이디를 확인해주세요.");
        }else if(!(passwordEncoder.matches(login.getPassword(), account.getPassword()))){ // 비밀번호가 틀리다면
            errors.rejectValue("password", "key","비밀번호를 확인해주세요");
        }
    }
}
