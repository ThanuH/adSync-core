package com.project.adsync.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.adsync.model.BasicAdDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadAdReq {
    private int userId;
    private int busCatId;
    private String url;
    private List<BasicAdDetails> priorityList;
    private boolean isUpdate;
    private int userAdId;
}
