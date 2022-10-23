package com.myapp.canhvm.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private Long id;
    private String account;
    private String userName;
    private String password;
}
