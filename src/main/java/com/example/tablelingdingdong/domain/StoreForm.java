package com.example.tablelingdingdong.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StoreForm {
    private String name;
    private String number;
    private String description;
    private String location;
}
