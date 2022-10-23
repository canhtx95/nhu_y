package com.myapp.canhvm.config;

import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.exception.CustomRuntimeException;
import com.myapp.canhvm.response.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApplicationExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse> handleBaseException(BaseException ex) {
        return new ResponseEntity<ApiResponse>(ApiResponse.fail(ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ApiResponse> handleCustomRuntimeException(CustomRuntimeException ex) {
        return new ResponseEntity<ApiResponse>(ApiResponse.fail(ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse> aaa(Exception ex) {
        return new ResponseEntity<ApiResponse>(ApiResponse.fail("ex.getMessage()"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
