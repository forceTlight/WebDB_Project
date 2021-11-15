package com.gachon_food.dto;

import lombok.*;


public class AccountDto {
//    private int userId;
//    private String name;
//    private String email;
//    private String password;
//    private String major;
//    private String deleteYN;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AccountRequestDto{
        private String email;
        private String password;
        private String password_confirm;
        @Builder
        public AccountRequestDto(String email, String password, String password_confirm){
            this.email = email;
            this.password = password;
            this.password_confirm = password_confirm;
        }
    }
}
