package com.example.tablelingdingdong.application;


import com.example.tablelingdingdong.config.security.JwtAuthenticationProvider;
import com.example.tablelingdingdong.domain.SignInForm;
import com.example.tablelingdingdong.domain.model.Customer;
import com.example.tablelingdingdong.domain.model.Manager;
import com.example.tablelingdingdong.exception.CustomException;
import com.example.tablelingdingdong.exception.ErrorCode;
import com.example.tablelingdingdong.service.CustomerService;
import com.example.tablelingdingdong.service.ManagerService;

import com.example.tablelingdingdong.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {
    private final ManagerService managerService;
    private final CustomerService customerService;
    private final JwtAuthenticationProvider provider;
    public String managerLoginToken(SignInForm signInForm){
       // 1. check valid user
        Manager manager = managerService.findValidManager(
                signInForm.getEmail(), signInForm.getPassword());
        // 2. generate token
        // 3. return token
        return provider.createToken(manager.getEmail(),manager.getId(), UserType.MANAGER);
    }
    public String customerLoginToken(SignInForm signInForm){
        // 1. check valid user
        Customer customer = customerService.findValidManager(
                signInForm.getEmail(), signInForm.getPassword());
        // 2. generate token
        // 3. return token
        return provider.createToken(customer.getEmail(),customer.getId(), UserType.CUSTOMER);
    }
}
