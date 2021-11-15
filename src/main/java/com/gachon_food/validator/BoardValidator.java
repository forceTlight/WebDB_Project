package com.gachon_food.validator;

import com.gachon_food.domain.Board;
import org.hibernate.validator.internal.engine.groups.ValidationOrder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
            errors.rejectValue("content", "key", "내용을 입력하세요");
        }
    }
}

