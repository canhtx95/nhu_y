package com.myapp.canhvm.request;

import lombok.Data;

@Data
public class ModifyItemQuantityRequest {
    private Integer userId;
    private Integer itemId;
    private Integer quantity;
    private Long prices;
}
