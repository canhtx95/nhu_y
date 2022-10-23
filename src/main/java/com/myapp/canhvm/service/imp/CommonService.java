package com.myapp.canhvm.service.imp;

import com.myapp.canhvm.constant.Message;
import com.myapp.canhvm.entity.Item;
import com.myapp.canhvm.entity.User;
import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.repository.ItemRepository;
import com.myapp.canhvm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class CommonService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;

    User validateUser(Integer userId) throws BaseException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new BaseException(Message.USER_NOT_EXIST);
        }
        return userOptional.get();
    }

    Item validateItem(Integer itemId) throws BaseException {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new BaseException(Message.ITEM_NOT_EXIST);
        }
        return itemOptional.get();
    }
}
