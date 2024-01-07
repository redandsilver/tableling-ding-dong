package com.example.tablelingdingdong.controller;

import com.example.tablelingdingdong.application.SignInApplication;
import com.example.tablelingdingdong.domain.form.SignInForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signin")
@RequiredArgsConstructor
public class SignInController {
    private final SignInApplication signInApplication;
    @PostMapping("/manager")
    public ResponseEntity<String> signInManager(@RequestBody SignInForm form){
        return ResponseEntity.ok(signInApplication.managerLoginToken(form));
    }
    @PostMapping("/customer")
    public ResponseEntity<String> signInCustomer(@RequestBody SignInForm form){
        return ResponseEntity.ok(signInApplication.customerLoginToken(form));
    }

}
