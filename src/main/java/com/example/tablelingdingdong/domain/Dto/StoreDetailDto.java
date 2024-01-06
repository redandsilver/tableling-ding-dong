package com.example.tablelingdingdong.domain.Dto;

import com.example.tablelingdingdong.domain.model.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StoreDetailDto {
    private Long id;
    private String storeName;
    private String location;
    private String description;
    private String storeNumber;
    public static StoreDetailDto from(Store store){
        return new StoreDetailDto(
                store.getId(),store.getStoreName(), store.getStoreName(), store.getDescription(), store.getStoreNumber()
        );

    }
}
