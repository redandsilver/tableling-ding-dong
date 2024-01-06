package com.example.tablelingdingdong.domain.Dto;

import com.example.tablelingdingdong.domain.model.Manager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ManagerDto {
    private Long id;
    private String email;
    public static ManagerDto from(Manager manager){
        return new ManagerDto(manager.getId(),manager.getEmail());

    }
}
