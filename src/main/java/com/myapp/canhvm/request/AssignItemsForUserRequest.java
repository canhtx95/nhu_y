package com.myapp.canhvm.request;

import com.myapp.canhvm.dto.ItemAssignDto;
import com.myapp.canhvm.entity.UserItem;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class AssignItemsForUserRequest {
    private Integer userId;
    private List<ItemAssignDto> items;
}
