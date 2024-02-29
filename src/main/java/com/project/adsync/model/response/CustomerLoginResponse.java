package com.project.adsync.model.response;

import com.project.adsync.domain.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerLoginResponse {
    int customerId;
    String customerName;
    String customerEmail;
    UserRole userRole;
}
