package com.project.adsync.exception;

import com.project.adsync.enums.ApplicationError;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AdsyncException extends RuntimeException{
    private String errorCode;
    private String errorMessage;

    public AdsyncException(ApplicationError applicationError) {
        this.errorCode = applicationError.statusCode();
        this.errorMessage = applicationError.stausMessage();
    }

    public AdsyncException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
