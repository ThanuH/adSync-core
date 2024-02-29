package com.project.adsync.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasicAdDetails {

    private int priority;
    private String ageRange;
    private String gender;
}
