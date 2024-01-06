package com.example.tablelingdingdong.domain.Dto;

import com.example.tablelingdingdong.domain.model.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StoreDto {
    private Long id;
    private String storeName;
    private String description;
    public static StoreDto from(Store store){
        return new StoreDto(
                store.getId(), store.getStoreName(), store.getDescription()
        );

    }
}
