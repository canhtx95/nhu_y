package com.myapp.canhvm.service;

import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.request.AssignItemsForUserRequest;
import com.myapp.canhvm.request.CreateUserRequest;
import com.myapp.canhvm.response.ApiResponse;

import java.util.List;

public interface UserService {
    ApiResponse findAll() throws BaseException;
    ApiResponse createUser(CreateUserRequest createUserRequest) throws BaseException;
    ApiResponse assignItemsForUser(AssignItemsForUserRequest request) throws BaseException;
}
