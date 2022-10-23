package com.myapp.canhvm.service;

import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.request.ModifyItemQuantityRequest;
import com.myapp.canhvm.request.TradeItemsRequest;
import com.myapp.canhvm.response.ApiResponse;

public interface MarketService {
   ApiResponse modifyItemQuantityInInventory(ModifyItemQuantityRequest request) throws BaseException;
   ApiResponse modifyItemQuantityOnMarket(ModifyItemQuantityRequest request) throws BaseException;
   ApiResponse tradeItems(TradeItemsRequest request) throws BaseException;
}
