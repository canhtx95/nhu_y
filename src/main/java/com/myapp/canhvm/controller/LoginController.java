package com.myapp.canhvm.controller;

import com.myapp.canhvm.entity.User;
import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.repository.UserRepository;
import com.myapp.canhvm.request.AssignItemsForUserRequest;
import com.myapp.canhvm.request.CreateUserRequest;
import com.myapp.canhvm.request.LoginRequest;
import com.myapp.canhvm.response.ApiResponse;
import com.myapp.canhvm.security.JwtTokenProvider;
import com.myapp.canhvm.security.SecurityUserDetails;
import com.myapp.canhvm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/dang-nhap")
    public ApiResponse login(@RequestBody LoginRequest request) throws BaseException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        return ApiResponse.success("Login success", jwtTokenProvider.generateToken(userDetails));
    }

    @PostMapping("/dang-ky")
    public ResponseEntity<Object> register(@RequestBody LoginRequest request) throws BaseException {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(ApiResponse.fail("Username does exist"));
        }
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping("/dang-xuat")
    public ResponseEntity<Object> logout(@RequestBody LoginRequest request) throws BaseException {
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(null);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() throws BaseException {
        SecurityContext sc = SecurityContextHolder.getContext();
        return ResponseEntity.ok("OK");
    }

}
