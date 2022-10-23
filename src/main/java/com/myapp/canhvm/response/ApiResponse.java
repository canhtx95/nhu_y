package com.myapp.canhvm.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myapp.canhvm.constant.ErrorResponseCode;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private Integer errorCode;
    private String message;
    private Object data;
    private Object optional;
    private LocalDateTime timeStamp;

    public static ApiResponse success(String message, Object data){
        ApiResponse response = new ApiResponse();
        response.setErrorCode(ErrorResponseCode.SUCCESS);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
    public static ApiResponse success(String message, Object data, Object optional){
        ApiResponse response = new ApiResponse();
        response.setErrorCode(ErrorResponseCode.SUCCESS);
        response.setMessage(message);
        response.setData(data);
        response.setOptional(optional);
        return response;
    }

    public static ApiResponse fail(String message){
        ApiResponse response = new ApiResponse();
        response.setErrorCode(ErrorResponseCode.FAIL);
        response.setMessage(message);
        response.setTimeStamp(LocalDateTime.now());
        return response;
    }

    public static ApiResponse fail(String message,Object data){
        ApiResponse response = new ApiResponse();
        response.setErrorCode(ErrorResponseCode.FAIL);
        response.setMessage(message);
        response.setTimeStamp(LocalDateTime.now());
        response.setData(data);
        return response;
    }
}
