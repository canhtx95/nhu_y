package com.myapp.canhvm.controller;

import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.request.ModifyItemQuantityRequest;
import com.myapp.canhvm.request.TradeItemsRequest;
import com.myapp.canhvm.response.ApiResponse;
import com.myapp.canhvm.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/market")
public class MarketController {

    /**
     * assumption item only transfer from inventory to market and reverse
     */


    // API to sell item from inventory
    @Autowired
    MarketService marketService;
    @PostMapping("/sell-items")
    ApiResponse sellItemFromInventory(@RequestBody ModifyItemQuantityRequest request) throws BaseException {
        return marketService.modifyItemQuantityInInventory(request);
    }

    //api to decrease/increase quantity on market
    @PostMapping("/modify-quantity-on-market")
    ApiResponse modifyQuantityOnMarket(@RequestBody ModifyItemQuantityRequest request) throws BaseException {
        return marketService.modifyItemQuantityOnMarket(request);
    }
    //api to decrease/increase quantity on market
    @PostMapping("/trade-items")
    ApiResponse tradeItems(@RequestBody TradeItemsRequest request) throws BaseException {
        return marketService.tradeItems(request);
    }
}
