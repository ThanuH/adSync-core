package com.project.adsync.enums;

public enum Status {
    PENDING_STATUS("P","PENDING"),
    ACTIVE_STATUS("A","ACTIVE"),
    APPROVED_STATUS("A","APPROVED"),
    DECLINED_STATUS("D","DECLINED"),
    BLOCKED_STATUS("B","BLOCKED" );

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
