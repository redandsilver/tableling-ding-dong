package com.example.tablelingdingdong.domain.form;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationForm {
    private String reservationName;
    private LocalDateTime reservationTime;
}
