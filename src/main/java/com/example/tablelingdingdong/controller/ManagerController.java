package com.example.tablelingdingdong.controller;


import com.example.tablelingdingdong.config.security.JwtAuthenticationProvider;
import com.example.tablelingdingdong.domain.Dto.ManagerDto;
import com.example.tablelingdingdong.domain.Dto.UserVo;
import com.example.tablelingdingdong.domain.StoreForm;
import com.example.tablelingdingdong.domain.model.Manager;
import com.example.tablelingdingdong.domain.model.Store;
import com.example.tablelingdingdong.exception.CustomException;
import com.example.tablelingdingdong.exception.ErrorCode;
import com.example.tablelingdingdong.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final JwtAuthenticationProvider provider;
    private final ManagerService managerService;

    @GetMapping("/getInfo")
    public ResponseEntity<ManagerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN")String token){
        UserVo vo = provider.getUserVo(token);
        Manager manager = managerService.findByIdAndEmail(vo.getId(),vo.getEmail()).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_EXIST_USER)
        );
        return ResponseEntity.ok(ManagerDto.from(manager));
    }

    @PutMapping("/join/partnership")
    public ResponseEntity<String> joinPartnership(@RequestHeader(name = "X-AUTH-TOKEN")String token){
        UserVo vo = provider.getUserVo(token);

        managerService.joinPartnership(vo);
        return ResponseEntity.ok("파트너십에 가입되었습니다. 가게를 등록해보세요!");
    }

    @PostMapping("/register/store")
    public ResponseEntity<String> registerStore(@RequestHeader(name = "X-AUTH-TOKEN")String token,
                                                @RequestBody StoreForm storeForm){
        UserVo vo = provider.getUserVo(token);
        managerService.registerStore(vo,storeForm);
        return ResponseEntity.ok(" 가게가 등록되었습니다. ");
    }
    @GetMapping("/all/stores")
    public ResponseEntity<List<Store>> getAllStores(@RequestHeader(name = "X-AUTH-TOKEN")String token){
        UserVo vo = provider.getUserVo(token);

        return ResponseEntity.ok(managerService.getAllStores(vo));
    }

}
