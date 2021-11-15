package com.gachon_food.controller;

import com.gachon_food.dto.AccountDto;
import com.gachon_food.service.AccountService;
import com.gachon_food.validator.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountValidator accountValidator;

    @GetMapping("/login")
    public String login(){
        return "/account/login";
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("accountRequestDto", new AccountDto.AccountRequestDto());
        return "/account/register";
    }
    @PostMapping("/register")
    public String register(@Valid AccountDto.AccountRequestDto accountRequestDto, BindingResult bindingResult){
        accountValidator.validate(accountRequestDto, bindingResult);
        System.out.println(bindingResult.hasErrors());
        if(bindingResult.hasErrors()){
            return "/account/register"; // 실패
        }
        else {
            // 성공
            accountService.save(accountRequestDto);
            return "redirect:/";
        }
    }
}
