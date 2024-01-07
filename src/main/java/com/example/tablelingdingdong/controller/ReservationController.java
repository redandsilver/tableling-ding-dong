package com.example.tablelingdingdong.controller;

import com.example.tablelingdingdong.config.security.JwtAuthenticationProvider;
import com.example.tablelingdingdong.domain.Dto.UserVo;
import com.example.tablelingdingdong.domain.form.ReservationForm;
import com.example.tablelingdingdong.domain.model.Customer;
import com.example.tablelingdingdong.domain.model.Manager;
import com.example.tablelingdingdong.exception.CustomException;
import com.example.tablelingdingdong.exception.ErrorCode;
import com.example.tablelingdingdong.service.CustomerService;
import com.example.tablelingdingdong.service.ManagerService;
import com.example.tablelingdingdong.service.ReservationServcie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;
    private final ReservationServcie reservationServcie;
    private final ManagerService managerService;
    @PostMapping("customer/{storeId}")
    public ResponseEntity<?> makeReservation(
            @RequestHeader(name = "X-AUTH-TOKEN")String token, @RequestBody ReservationForm form, @PathVariable Long storeId){
        UserVo vo = provider.getUserVo(token);
        Customer customer = customerService.findByIdAndEmail(vo.getId(),vo.getEmail()).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_EXIST_USER)
        );

        return ResponseEntity.ok( reservationServcie.makeReservation(customer,form,storeId));
    }
    @PostMapping("manager/{reservationId}")
    public ResponseEntity<?> confirmReservation(
            @RequestHeader(name = "X-AUTH-TOKEN")String token, @RequestBody ReservationForm form, @PathVariable Long reservationId){
        UserVo vo = provider.getUserVo(token);
        Manager manager = managerService.findByIdAndEmail(vo.getId(),vo.getEmail()).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_EXIST_USER)
        );

        return ResponseEntity.ok( reservationServcie.confirmReservation(manager,reservationId));
    }
}
