package com.example.tablelingdingdong.controller;
import com.example.tablelingdingdong.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    // 기본 화면: 모든 가게들 보여주기
    @GetMapping("/home")
    public ResponseEntity<?> getAllStores(){
        return ResponseEntity.ok(storeService.getAllStores());
    }
    // 가게 상세 페이지
    @GetMapping("/store/{id}")
    public ResponseEntity<?> getAllStores(@PathVariable Long id){
        return ResponseEntity.ok(storeService.getStoreDetail(id));
    }
}
