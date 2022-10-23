package com.myapp.canhvm.service.imp;

import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.facade.ItemFacade;
import com.myapp.canhvm.repository.UserRepository;
import com.myapp.canhvm.request.ModifyItemQuantityRequest;
import com.myapp.canhvm.request.TradeItemsRequest;
import com.myapp.canhvm.response.ApiResponse;
import com.myapp.canhvm.service.MarketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketServiceImpl implements MarketService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemFacade itemFacade;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public ApiResponse modifyItemQuantityInInventory(ModifyItemQuantityRequest request) throws BaseException {
        return itemFacade.modifyItemQuantityInInventory(request);
    }

    @Override
    public ApiResponse modifyItemQuantityOnMarket(ModifyItemQuantityRequest request) throws BaseException {
        return itemFacade.modifyItemQuantityOnMarket(request);
    }

    @Override
    public ApiResponse tradeItems(TradeItemsRequest request) throws BaseException {
        return itemFacade.tradeItems(request);
    }
}
