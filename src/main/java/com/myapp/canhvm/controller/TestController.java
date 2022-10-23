package com.myapp.canhvm.controller;

import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.exception.CustomRuntimeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test1")
    public void getAllUser() {
        throw new CustomRuntimeException("hello");
    }
}
