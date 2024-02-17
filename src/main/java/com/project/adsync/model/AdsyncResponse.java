package com.project.adsync.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AdsyncResponse {

    private String responseCode;
    private Object responseObject;
}

