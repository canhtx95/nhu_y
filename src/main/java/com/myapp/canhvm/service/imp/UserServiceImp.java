package com.myapp.canhvm.service.imp;

import com.myapp.canhvm.entity.User;
import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.facade.ItemFacade;
import com.myapp.canhvm.repository.UserRepository;
import com.myapp.canhvm.request.AssignItemsForUserRequest;
import com.myapp.canhvm.request.CreateUserRequest;
import com.myapp.canhvm.response.ApiResponse;
import com.myapp.canhvm.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp extends CommonService implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemFacade itemFacade;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse findAll() throws BaseException {
        List<User> users;
        try {
            users = userRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("error when get users");
        }
        return ApiResponse.success("OK",users);
    }


    @Override
    @Transactional
    public ApiResponse createUser(CreateUserRequest createUserRequest) throws BaseException {
        validateRequestCreateUser(createUserRequest);
        User userEntity = modelMapper.map(createUserRequest,User.class);
        userEntity.setId(null);
        userEntity.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        userEntity.setCreatedDated(LocalDateTime.now());
        return ApiResponse.success("OK",userRepository.save(userEntity));
    }

    void validateRequestCreateUser(CreateUserRequest createUserRequest) throws BaseException {
        Optional<User> checkExist = userRepository.findByUsername(createUserRequest.getUserName());
       if(checkExist.isPresent()){
           throw new BaseException("user name is existing");
       }
    }

    @Override
    @Transactional
    public ApiResponse assignItemsForUser(AssignItemsForUserRequest request) throws BaseException {
        validateUser(request.getUserId());
        return itemFacade.assignItemForUser(request);
    }
}
