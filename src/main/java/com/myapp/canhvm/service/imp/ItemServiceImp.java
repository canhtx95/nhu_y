package com.myapp.canhvm.service.imp;

import com.myapp.canhvm.constant.GiveRandomItemQuantity;
import com.myapp.canhvm.entity.Item;
import com.myapp.canhvm.entity.UserItem;
import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.repository.ItemRepository;
import com.myapp.canhvm.repository.UserItemRepository;
import com.myapp.canhvm.request.AddItemRequest;
import com.myapp.canhvm.response.ApiResponse;
import com.myapp.canhvm.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemServiceImp extends CommonService implements ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserItemRepository userItemRepository;
    @Autowired
    ModelMapper modelMapper;

    final int LEVEL_ITEM_1 = 10;
    final int LEVEL_ITEM_2 = 30;
    final int LEVEL_ITEM_3 = 60;
    final int LEVEL_ITEM_4 = 60;

    /*
    - 1: loi out of index
    - 2: khi assign item cho user
     */

    @Override
    public ApiResponse findAll() {
        return ApiResponse.success("OK", itemRepository.findAll());
    }

    @Transactional
    public ApiResponse addItems(List<AddItemRequest> itemRequests) {
        List<Item> items = itemRequests.stream().map(i -> {
            Item item = modelMapper.map(i, Item.class);
            item.setCreatedDated(LocalDateTime.now());
            return item;
        }).filter(item -> {
            Optional<Item> itemOptional = itemRepository.findByItemCode(item.getItemCode());
            return !item.getItemCode().equals(itemOptional.orElse(new Item()).getItemCode());
        }).collect(Collectors.toList());
        itemRepository.saveAll(items);
        return ApiResponse.success("OK", items);
    }

    @Override
    @Transactional
    public ApiResponse giveRandomItemForUser(Integer userId) throws BaseException {
        validateUser(userId);
        Set<Integer> rangeLevel_1 = new HashSet<>(Arrays.asList(1));
        Set<Integer> rangeLevel_2 = new HashSet<>(Arrays.asList(2, 3, 4));
        Set<Integer> rangeLevel_3 = new HashSet<>(Arrays.asList(5, 6, 7, 8, 9, 10));
        List<Integer> idItemAssignToUser = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < GiveRandomItemQuantity.QUANTITY_ITEM_PER_DAY; i++) {
            Integer value = random.nextInt(10) + 1;
            List<Integer> allItemByLevel = new ArrayList<>();
            if (rangeLevel_1.contains(value)) {
                allItemByLevel = itemRepository.findItemByLevel(3);
            }
            if (rangeLevel_2.contains(value)) {
                allItemByLevel = itemRepository.findItemByLevel(2);
            }
            if (rangeLevel_3.contains(value)) {
                allItemByLevel = itemRepository.findItemByLevel(1);
            }
            int index = getValueRandom(allItemByLevel.size());
            int itemId = allItemByLevel.get(index);
            idItemAssignToUser.add(itemId);
        }
        return ApiResponse.success("ok", assignItemForUser(userId, idItemAssignToUser));
    }

    ApiResponse assignItemForUser(Integer userId, List<Integer> itemIds) {
        ApiResponse response = new ApiResponse();
        List<String> responseMessage = new ArrayList<>();
        List<UserItem> userItemList = new ArrayList<>();
        List<UserItem> itemsBelongToUser = userItemRepository.findByUserIdAndItemIds(userId, itemIds);
        Map<Integer, Integer> itemIdToDuplicate = new HashMap<>();
        for (Integer id : itemIds) {
            if (itemIdToDuplicate.containsKey(id)) {
                int quantityDuplicate = itemIdToDuplicate.get(id);
                quantityDuplicate++;
                itemIdToDuplicate.put(id, quantityDuplicate);
            } else itemIdToDuplicate.put(id, 1);
        }

        for (UserItem item : itemsBelongToUser) {
            if (itemIdToDuplicate.containsKey(item.getItemId())) {
                int receiveQuantity = GiveRandomItemQuantity.QUANTITY_EACH_ITEM_PER_DAY * itemIdToDuplicate.get(item.getItemId());
                int newQuantity = item.getQuantityInInventory() + receiveQuantity;
                item.setQuantityInInventory(newQuantity);
                userItemList.add(item);
                responseMessage.add("Chuc mung ban nhan dc " + receiveQuantity + " - itemId: " + item.getItemId());
                itemIdToDuplicate.remove(item.getItemId());
            }
        }
        for (Map.Entry<Integer, Integer> map : itemIdToDuplicate.entrySet()) {
            int receiveQuantity = map.getValue() * GiveRandomItemQuantity.QUANTITY_EACH_ITEM_PER_DAY;
            UserItem userItem = new UserItem(userId, map.getKey(), receiveQuantity);
            userItemList.add(userItem);
            responseMessage.add("Chuc mung ban nhan dc " + receiveQuantity + " - itemId: " + userItem.getItemId());
        }
        response.setData(responseMessage);
        response.setOptional(userItemRepository.saveAll(userItemList));
        response.setErrorCode(200);
        response.setMessage("OK");
        return response;
    }

    Integer getValueRandom(Integer bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }
}
