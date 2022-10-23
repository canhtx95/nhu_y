package com.myapp.canhvm.controller;

import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.request.AssignItemsForUserRequest;
import com.myapp.canhvm.request.CreateUserRequest;
import com.myapp.canhvm.response.ApiResponse;
import com.myapp.canhvm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUser() throws BaseException {
        ApiResponse response = userService.findAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest createUserRequest) throws BaseException {
        ApiResponse response = userService.createUser(createUserRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/assign-item-for-user")
    public ResponseEntity<ApiResponse> assignItemForUser(@RequestBody AssignItemsForUserRequest request) throws BaseException {
        ApiResponse response = userService.assignItemsForUser(request);
        return ResponseEntity.ok(response);
    }
}
