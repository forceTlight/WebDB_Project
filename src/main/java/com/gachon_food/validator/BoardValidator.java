package com.gachon_food.validator;

import com.gachon_food.domain.Board;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// 게시글 관련 검증 클래스
@Component
public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz){
        return Board.class.equals(clazz);
    }
    @Override
    public void validate(Object obj, Errors errors){
        Board b = (Board) obj;
        if(StringUtils.isEmpty(b.getContent())){
            // 내용을 아무것도 입력하지 않으면 에러
            errors.rejectValue("content", "key", "내용을 입력하세요");
        }
    }
}

