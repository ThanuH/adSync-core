package com.project.adsync.exception;

import com.project.adsync.enums.AdsyncApplicationError;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@EqualsAndHashCode(callSuper=false)
public class AdsyncException extends RuntimeException{

    private Integer code;
    private String message;
    private HttpStatus status;

    public AdsyncException(AdsyncApplicationError applicationError) {
        super();
        this.code = applicationError.code();
        this.message = applicationError.statusMessage();
        this.status = applicationError.status();
    }




}
