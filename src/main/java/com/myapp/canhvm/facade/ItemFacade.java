package com.myapp.canhvm.facade;

import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.request.AssignItemsForUserRequest;
import com.myapp.canhvm.request.ModifyItemQuantityRequest;
import com.myapp.canhvm.request.TradeItemsRequest;
import com.myapp.canhvm.response.ApiResponse;

public interface ItemFacade {
   ApiResponse assignItemForUser(AssignItemsForUserRequest requests);

   ApiResponse modifyItemQuantityInInventory(ModifyItemQuantityRequest requests) throws BaseException;

   ApiResponse modifyItemQuantityOnMarket(ModifyItemQuantityRequest requests) throws BaseException;
   ApiResponse tradeItems(TradeItemsRequest requests) throws BaseException;
}
