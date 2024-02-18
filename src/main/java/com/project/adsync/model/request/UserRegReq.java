package com.project.adsync.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegReq {

    String userName;
    String contactNo;
    String email;
    String businessName;
    String businessRegNo;
    int businessCategory;
    String password;
}