package com.myapp.canhvm.facade.impl;

import com.myapp.canhvm.entity.User;
import com.myapp.canhvm.entity.UserItem;
import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.facade.ItemFacade;
import com.myapp.canhvm.repository.ItemRepository;
import com.myapp.canhvm.repository.UserItemRepository;
import com.myapp.canhvm.repository.UserRepository;
import com.myapp.canhvm.request.AssignItemsForUserRequest;
import com.myapp.canhvm.request.ModifyItemQuantityRequest;
import com.myapp.canhvm.request.TradeItemsRequest;
import com.myapp.canhvm.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemFacadeImpl implements ItemFacade {
    @Autowired
    UserItemRepository userItemRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    final String ITEM_NOT_EXISTING = "ITEM_NOT_EXISTING";
    final String ITEM_ASSIGNED_TO_USER_BEFORE = "ITEM_ASSIGNED_TO_USER_BEFORE";

    @Override
    @Transactional
    public ApiResponse assignItemForUser(AssignItemsForUserRequest requests) {
        Map<String, Set<Integer>> errorResponse = errorResponsesWhenAssignItemForUser();
        Set<Long> listItemsExisting = userItemRepository.findItemsIdByUserId(requests.getUserId());
        List<UserItem> listItems = requests.getItems().stream().map(i -> {
            UserItem userItem = modelMapper.map(i, UserItem.class);
            userItem.setUserId(requests.getUserId());
            userItem.setQuantityInInventory(i.getQuantity());
            userItem.setQuantityOnMarket(0);
            userItem.setPriceOnMarket(0L);
            userItem.setCreatedDated(LocalDateTime.now());
            return userItem;
        }).filter(item -> {
            Integer itemId = item.getItemId();
            if (itemRepository.findById(itemId).isEmpty()) {
                errorResponse.get(ITEM_NOT_EXISTING).add(itemId);
                return false;
            }
            if (listItemsExisting.contains(itemId)) {
                errorResponse.get(ITEM_ASSIGNED_TO_USER_BEFORE).add(itemId);
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        userItemRepository.saveAll(listItems);
        return ApiResponse.success("items Id not valid", errorResponse);
    }

    Map<String, Set<Integer>> errorResponsesWhenAssignItemForUser() {
        Map<String, Set<Integer>> res = new HashMap<>();
        res.put(ITEM_NOT_EXISTING, new HashSet<>());
        res.put(ITEM_ASSIGNED_TO_USER_BEFORE, new HashSet<>());
        return res;
    }

    @Override
    @Transactional
    public ApiResponse modifyItemQuantityInInventory(ModifyItemQuantityRequest request) throws BaseException {
        Optional<UserItem> itemRes = userItemRepository.findByUserIdAndItemId(request.getUserId(), request.getItemId());
        if (itemRes.isEmpty()) {
            throw new BaseException("item is not existing");
        }
        UserItem item = itemRes.get();
        Integer currentQuantityInInventory = item.getQuantityInInventory();
        Integer currentQuantityOnMarket = item.getQuantityOnMarket();
        Integer modifyQuantity = request.getQuantity();
        if (modifyQuantity > currentQuantityInInventory) {
            throw new BaseException("quantity exceeded allowed");
        }
        Integer quantityInInventoryUpdated = currentQuantityInInventory - modifyQuantity;
        item.setQuantityInInventory(quantityInInventoryUpdated);
        item.setQuantityOnMarket(currentQuantityOnMarket + modifyQuantity);
        item.setPriceOnMarket(request.getPrices());
        userItemRepository.save(item);
        return ApiResponse.success("OK", item);
    }

    @Override
    @Transactional
    public ApiResponse modifyItemQuantityOnMarket(ModifyItemQuantityRequest request) throws BaseException {
        Optional<UserItem> itemRes = userItemRepository.findByUserIdAndItemId(request.getUserId(), request.getItemId());
        if (itemRes.isEmpty()) {
            throw new BaseException("item is not existing or doesn't belong to user");
        }
        UserItem item = itemRes.get();
        Integer currentQuantityInInventory = item.getQuantityInInventory();
        Integer currentQuantityOnMarket = item.getQuantityOnMarket();
        Integer modifyQuantity = request.getQuantity();
        if (modifyQuantity > currentQuantityOnMarket) {
            throw new BaseException("quantity exceeded allowed");
        }
        Integer quantityInInventory = currentQuantityInInventory + modifyQuantity;
        item.setQuantityInInventory(quantityInInventory);
        item.setQuantityOnMarket(currentQuantityOnMarket - modifyQuantity);
        if (item.getQuantityOnMarket().equals(0)) {
            item.setPriceOnMarket(0L);
        }
        userItemRepository.save(item);
        return ApiResponse.success("OK", item);
    }

    @Override
    @Transactional
    public ApiResponse tradeItems(TradeItemsRequest request) throws BaseException {
        User seller = userRepository.findById(request.getSellerId()).get();
        User buyer = userRepository.findById(request.getBuyerId()).get();
        Optional<UserItem> sellerItemOptional = userItemRepository.findByUserIdAndItemId(seller.getId(), request.getItemId());
        if (sellerItemOptional.isEmpty()) {
            throw new BaseException("Seller does not own this item");
        }
        UserItem sellerItem = sellerItemOptional.get();
        Long billAmount = request.getQuantity() * sellerItem.getPriceOnMarket();
        Long buyerCash = buyer.getCash();
        if (request.getQuantity() > sellerItem.getQuantityOnMarket()) {
            throw new BaseException("Quantity exceeded allowed");

        }
        if (billAmount > buyerCash) {
            throw new BaseException("You don't have enough cash to buy this item");
        }

        Optional<UserItem> buyerItemOptional = userItemRepository.findByUserIdAndItemId(buyer.getId(), request.getItemId());
        if (buyerItemOptional.isEmpty()) {
            UserItem item = new UserItem(request.getItemId(), request.getBuyerId(), request.getQuantity());
            userItemRepository.save(item);

        } else {
            UserItem buyerItem = buyerItemOptional.get();
            Integer quantityOfBuyerItemAfterTrade = buyerItem.getQuantityInInventory() + request.getQuantity();
            buyerItem.setQuantityInInventory(quantityOfBuyerItemAfterTrade);
            userItemRepository.save(buyerItem);
        }

        Integer quantityOfSellerItemAfterTrade = sellerItem.getQuantityOnMarket() - request.getQuantity();
        sellerItem.setQuantityOnMarket(quantityOfSellerItemAfterTrade);
        userItemRepository.save(sellerItem);
        Long newCashOfSeller = seller.getCash() + billAmount;
        Long newCashOfBuyer = buyerCash - billAmount;
        seller.setCash(newCashOfSeller);
        buyer.setCash(newCashOfBuyer);
        userRepository.save(seller);
        userRepository.save(buyer);
        return ApiResponse.success("OK", null);
    }
}
