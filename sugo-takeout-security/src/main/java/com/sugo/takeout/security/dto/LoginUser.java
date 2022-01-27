package com.sugo.takeout.security.dto;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginUser {

    private String username;
    private String password;
    private Integer rememberMe;

}
