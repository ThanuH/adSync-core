package com.project.adsync.enums;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public enum AdsyncApplicationError {
    USER_ALREADY_EXIST(1, "User already exits", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CREDENTIALS(2, "Invalid Username or Password, Please Check again", HttpStatus.UNAUTHORIZED);

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
