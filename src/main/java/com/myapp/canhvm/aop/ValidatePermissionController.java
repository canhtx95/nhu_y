package com.myapp.canhvm.aop;

import com.myapp.canhvm.response.ApiResponse;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Aspect
@Configuration
public class ValidatePermissionController {
    @Autowired
    HttpServletRequest request;
    HttpServletRequest request2;
    @Before("execution(* com.myapp.canhvm.controller.*.*(..))")
    public ApiResponse validateUser(){
        return null;
    }

}
