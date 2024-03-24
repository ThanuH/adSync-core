package com.project.adsync.enums;

public enum Status {
    PENDING_STATUS("PENDING","PENDING"),
    ACTIVE_STATUS("ACTIVE","ACTIVE"),
    APPROVED_STATUS("APPROVED","APPROVED"),
    DECLINED_STATUS("DECLINED","DECLINED"),
    BLOCKED_STATUS("BLOCKED","BLOCKED" ),
    REJECTED_STATUS("REJECTED","REJECTED");

    private String status;
    private String description;

    private Status(String status , String description){
        this.status = status;
        this.description = description;
    }

    public String status(){
        return status;
    }

    public String description(){
        return description;
    }

}
