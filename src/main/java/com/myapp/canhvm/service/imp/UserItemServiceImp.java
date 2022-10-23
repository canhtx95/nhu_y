package com.myapp.canhvm.service.imp;

import com.myapp.canhvm.entity.UserItem;
import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.repository.UserItemRepository;
import com.myapp.canhvm.request.AssignItemsForUserRequest;
import com.myapp.canhvm.response.ApiResponse;
import com.myapp.canhvm.service.UserItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserItemServiceImp implements UserItemService {
    @Autowired
    UserItemRepository userItemRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ApiResponse findAll() throws BaseException {
        List<UserItem> users;
        try {
            users = userItemRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("error when get items of user");
        }
        return ApiResponse.success("OK",users);
    }

    @Override
    public ApiResponse addItemForUser(AssignItemsForUserRequest assignItemsForUserRequest) throws BaseException{
        return null;
    }


}
