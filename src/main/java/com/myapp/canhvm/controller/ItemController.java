package com.myapp.canhvm.controller;

import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.request.AddItemRequest;
import com.myapp.canhvm.response.ApiResponse;
import com.myapp.canhvm.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    ItemService itemService;

    @PostMapping("/random/{userId}")
    public ResponseEntity<ApiResponse> getRandomItemsPerDay(@PathVariable("userId") Integer userId) throws BaseException {
        ApiResponse response = itemService.giveRandomItemForUser(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-items")
    public ResponseEntity<ApiResponse> addItem(@RequestBody List<AddItemRequest> addItemRequest) throws BaseException {
        ApiResponse response = itemService.addItems(addItemRequest);
        return ResponseEntity.ok(response);
    }
}
