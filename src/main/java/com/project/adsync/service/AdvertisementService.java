package com.project.adsync.service;

import com.project.adsync.domain.*;
import com.project.adsync.model.request.UploadAdReq;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface AdvertisementService {

    List<BusinessCategory> getBussinessCategories();

    BusinessCategory getBusinessCategoryById(int id);

    String uploadUserAdvertisement(UploadAdReq uploadAdReq, User user);

    ResponseEntity<ByteArrayResource> analyzeDemographicData(List<DemographicData> demographicDataList);

    String deleteAd(int id, int adId);

    UserAdvertisement getUserAdvertisementById(int id);

    List<UserAdvertisement> getUserWisePendingAdvertisement(User user);

    List<UserAdvertisement> getAllPendingAdvertisement();

    List<UserAdvertisement> getAdByUniqueIdentifier(String uniquieIdentifier);

    void updateAdStatus(String status, List<UserAdvertisement> userAdvertisements);

    List<UserAdvertisement> getAdByUser(int id);
}