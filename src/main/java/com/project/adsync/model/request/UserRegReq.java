package com.project.adsync.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegReq {
    @NotBlank(message = "User name should not be null")
    String userName;
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number should be 10 digits")
    String contactNo;
    @Email(message = "Email should be valid")
    String email;
    @NotBlank(message = "Business name should not be null")
    String businessName;
    @NotBlank(message = "Business registration number should not be null")
    String businessRegNo;
    int businessCategory;
    String password;
}