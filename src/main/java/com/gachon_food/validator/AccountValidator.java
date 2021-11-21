package com.gachon_food.validator;

import com.gachon_food.domain.Account;
import com.gachon_food.dto.AccountDto;
import com.gachon_food.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// 회원가입 관련 검증 클래스
@Component
public class AccountValidator implements Validator {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        AccountDto.AccountRequestDto account = (AccountDto.AccountRequestDto) obj;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!(account.getPassword().equals((account.getPassword_confirm())))){
            // 비밀번호와 비밀번호 확인이 다르다면
            errors.rejectValue("password", "key", "비밀번호가 일치하지 않습니다.");
        } // 이미 존재하는 사용자 이름이라면
        else if(accountRepository.findByEmail(account.getEmail()).isPresent()){
            errors.rejectValue("email", "key", "이미 사용자 이름이 존재합니다.");
        }
    }
}
