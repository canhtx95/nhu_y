package com.myapp.canhvm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddItemRequest {
    private String itemCode;
    private String itemName;
    private Integer level;
    private String description;

}
