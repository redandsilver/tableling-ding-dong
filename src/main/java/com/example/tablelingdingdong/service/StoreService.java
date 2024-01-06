package com.example.tablelingdingdong.service;

import com.example.tablelingdingdong.domain.Dto.StoreDetailDto;
import com.example.tablelingdingdong.domain.Dto.StoreDto;
import com.example.tablelingdingdong.domain.model.Store;
import com.example.tablelingdingdong.domain.repository.StoreRepository;
import com.example.tablelingdingdong.exception.CustomException;
import com.example.tablelingdingdong.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    public List<StoreDto> getAllStores() {
        List<Store> storeList = storeRepository.findAll();
        return storeList.stream()
                .map(StoreDto::from)
                .collect(Collectors.toList());
    }

    public StoreDetailDto getStoreDetail(Long id) {
        Store store = storeRepository.getById(id);
        if(store == null){
            throw new CustomException(ErrorCode.NOT_EXIST_STORE);
        }
        return StoreDetailDto.from(store);
    }
}
