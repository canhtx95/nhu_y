package com.myapp.canhvm.request;

import lombok.Data;

@Data
public class TradeItemsRequest {
    private Integer sellerId;
    private Integer buyerId;
    private Integer itemId;
    private Integer quantity;
}
