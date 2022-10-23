package com.myapp.canhvm.service;

import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.request.AssignItemsForUserRequest;
import com.myapp.canhvm.response.ApiResponse;

public interface UserItemService {
    ApiResponse findAll() throws BaseException;

    ApiResponse addItemForUser(AssignItemsForUserRequest assignItemsForUserRequest) throws BaseException;
}
