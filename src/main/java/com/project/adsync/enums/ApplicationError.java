package com.project.adsync.enums;

public enum ApplicationError {
    USER_ALREADY_EXISTS("000", "User already exists");

    private String statusCode;
    private String statusMessage;

    private ApplicationError(String statusCode , String statusMessage){
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public String statusCode(){
        return statusCode;
    }

    public String stausMessage(){
        return statusMessage;
    }
}
