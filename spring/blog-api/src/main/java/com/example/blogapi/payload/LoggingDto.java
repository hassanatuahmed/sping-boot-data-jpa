package com.example.blogapi.payload;

import lombok.Data;

@Data
public class LoggingDto {
    private  String usernameOrEmail;
    private String password;
}
