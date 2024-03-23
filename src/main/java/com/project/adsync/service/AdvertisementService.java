package com.project.adsync.service;

import com.project.adsync.domain.Advertisement;
import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.DemographicData;
import com.project.adsync.domain.User;
import com.project.adsync.model.request.UploadAdReq;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AdvertisementService {

    List<BusinessCategory> getBussinessCategories();

    BusinessCategory getBusinessCategoryById(int id);

    String uploadUserAdvertisement(UploadAdReq uploadAdReq, User user);

    ResponseEntity<ByteArrayResource> analyzeDemographicData(List<DemographicData> demographicDataList);

//    String updateUserAdvertisement(UploadAdReq uploadAdReq);

}
