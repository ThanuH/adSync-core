package com.project.adsync.enums;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public enum AdsyncApplicationError {
    USER_ALREADY_EXIST(1, "User already exits", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CREDENTIALS(2, "Invalid Username or Password, Please Check again", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(3,"User not found" ,HttpStatus.UNAUTHORIZED ),
    USER_BLOCKED(4,"The User is blocked. Please Contact AdSync Administrators" ,HttpStatus.UNAUTHORIZED ),
    MEDIA_NOT_FOUND(5,"Advertisement media not found",HttpStatus.BAD_REQUEST);



    private Integer code;
    private String statusMessage;
    private HttpStatus status;

    private AdsyncApplicationError(Integer code, String message, HttpStatus status) {

        this.code = code;
        this.statusMessage = message;
        this.status = status;

    }

    public Integer code() {
        return code;
    }

    public String statusMessage() {
        return statusMessage;
    }

    public HttpStatus status() { return status; }
}
