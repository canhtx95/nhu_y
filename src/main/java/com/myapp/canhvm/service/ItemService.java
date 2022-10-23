package com.myapp.canhvm.service;

import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.request.AddItemRequest;
import com.myapp.canhvm.request.CreateUserRequest;
import com.myapp.canhvm.response.ApiResponse;

import java.util.List;

public interface ItemService {
    ApiResponse findAll();
    ApiResponse addItems(List<AddItemRequest> addItemRequest);
    ApiResponse giveRandomItemForUser(Integer userId) throws BaseException;
}
