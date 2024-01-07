package com.example.tablelingdingdong.controller;

import com.example.tablelingdingdong.config.security.JwtAuthenticationProvider;
import com.example.tablelingdingdong.domain.Dto.CustomerDto;
import com.example.tablelingdingdong.domain.Dto.UserVo;
import com.example.tablelingdingdong.domain.model.Customer;
import com.example.tablelingdingdong.exception.CustomException;
import com.example.tablelingdingdong.exception.ErrorCode;
import com.example.tablelingdingdong.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;
    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN")String token){
        UserVo vo = provider.getUserVo(token);
        Customer customer = customerService.findByIdAndEmail(vo.getId(),vo.getEmail()).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_EXIST_USER)
        );
        return ResponseEntity.ok(CustomerDto.from(customer));
    }
}
