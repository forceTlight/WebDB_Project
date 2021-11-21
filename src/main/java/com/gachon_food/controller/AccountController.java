package com.gachon_food.controller;

import com.gachon_food.dto.AccountDto;
import com.gachon_food.service.AccountService;
import com.gachon_food.service.LoginService;
import com.gachon_food.validator.AccountValidator;
import com.gachon_food.validator.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private LoginValidator loginValidator;
    /*
     로그인 화면 (GET)
     */

    @GetMapping("/login")
    public String loginForm(){
        //model.addAttribute("loginFormDto", new LoginFormDto());
        return "/account/login";
    }
    /*
    로그아웃 요청(POST)
     */
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();; // 세션을 제거한다
        }
        return "redirect:/";
    }
        /*
    회원가입 화면 (GET)
     */
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("accountRequestDto", new AccountDto.AccountRequestDto());
        return "/account/register";
    }
    /*
    회원가입 요청 (POST)
     */
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
