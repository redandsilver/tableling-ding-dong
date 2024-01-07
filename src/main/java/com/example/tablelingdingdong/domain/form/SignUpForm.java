package com.example.tablelingdingdong.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SignUpForm {
    private String email;
    private String name;
    private String password;
    private String phone;
}
