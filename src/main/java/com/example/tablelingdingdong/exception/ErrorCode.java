package com.example.tablelingdingdong.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // validation
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST,"이미 가입된 회원입니다."),
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST,"일치하는 회원이 없습니다."),
    NOT_EXIST_STORE(HttpStatus.BAD_REQUEST,"없는 가게입니다."),
    ALREADY_VERIFIED(HttpStatus.BAD_REQUEST,"이미 인증이 완료되었습니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST,"잘못된 인증 시도입니다."),
    EXPIRE_CODE(HttpStatus.BAD_REQUEST,"인증시간이 만료되었습니다."),
    ALREADY_EXIST_RESERVATION(HttpStatus.BAD_REQUEST,"해당 시간의 예약이 이미 있습니다."),
    NOT_EXIST_RESERVATION(HttpStatus.BAD_REQUEST,"없는 예약입니다."),
    // auth
    LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST,"아이디나 패스워드를 확인해주세요."),
    PARTNERSHIP_CHECK_FAIL(HttpStatus.FORBIDDEN, "파트너십에 가입후 이용할 수 있습니다.");


    private final HttpStatus httpStatus;
    private final String detail;
}
