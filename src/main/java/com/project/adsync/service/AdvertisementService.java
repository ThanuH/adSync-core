package com.project.adsync.service;

import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.User;
import com.project.adsync.model.request.UploadAdReq;

import java.util.List;

public interface AdvertisementService {

    List<BusinessCategory> getBussinessCategories();

    BusinessCategory getBusinessCategoryById(int id);

    String uploadUserAdvertisement(UploadAdReq uploadAdReq, User user);

//    String updateUserAdvertisement(UploadAdReq uploadAdReq);

}
