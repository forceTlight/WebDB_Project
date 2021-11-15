package com.gachon_food.validator;

import com.gachon_food.domain.Account;
import com.gachon_food.dto.AccountDto;
import com.gachon_food.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
        if(!(account.getPassword().equals((account.getPassword_confirm())))){
            // 비밀번호와 비밀번호 확인이 다르다면
            errors.rejectValue("password", "key", "비밀번호가 일치하지 않습니다.");
        }else if(accountRepository.findByEmail(account.getEmail()) != null){
            errors.rejectValue("accountname", "key", "이미 사용자 이름이 존재합니다.");
        }
    }
}
