package com.example.tablelingdingdong.controller;

import com.example.tablelingdingdong.application.SignUpApplication;
import com.example.tablelingdingdong.domain.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpApplication signUpApplication;
    // 회원가입
    @PostMapping("/manager")
    public ResponseEntity<String> managerSignUp(@RequestBody SignUpForm signUpForm){

        return ResponseEntity.ok(signUpApplication.signUpManager(signUpForm)+" 인증코드가 발송되었습니다.");
    }
    // 이메일 인증
    @GetMapping("/manager/verify")
    public ResponseEntity<String> verifyManager(String email, String code){
        signUpApplication.verifyManager(email,code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
    // 회원가입
    @PostMapping("/customer")
    public ResponseEntity<String> customerSignUp(@RequestBody SignUpForm signUpForm){

        return ResponseEntity.ok(signUpApplication.signUpCustomer(signUpForm)+" 인증코드가 발송되었습니다.");
    }
    // 이메일 인증
    @GetMapping("/customer/verify")
    public ResponseEntity<String> verifyCustomer(String email, String code){
        signUpApplication.verifyCustomer(email,code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
}
