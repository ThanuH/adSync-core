package com.project.adsync.service;

import com.project.adsync.domain.Advertisement;
import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.User;
import com.project.adsync.domain.UserAdvertisement;
import com.project.adsync.enums.Status;
import com.project.adsync.model.BasicAdDetails;
import com.project.adsync.model.request.UploadAdReq;
import com.project.adsync.repository.AdvertisemntRepository;
import com.project.adsync.repository.BusinessCategoryRepository;
import com.project.adsync.repository.UserAdvertisementRepository;
import com.project.adsync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementServiceImpl implements AdvertisementService{
    @Autowired
    private BusinessCategoryRepository businessCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAdvertisementRepository userAdvertisementRepository;

    @Autowired
    private AdvertisemntRepository advertisementRepository;

    @Override
    public List<BusinessCategory> getBussinessCategories() {
        return  businessCategoryRepository.findAll();
    }

    @Override
    public BusinessCategory getBusinessCategoryById(int id) {
        Optional<BusinessCategory> businessCategory = businessCategoryRepository.findById(id);
        return businessCategory.orElse(null);
    }

    @Override
    public String uploadUserAdvertisement(UploadAdReq uploadAdReq, User user) {
        Advertisement advertisement = new Advertisement();
        boolean isUrlUpdated = false;
        if (uploadAdReq.isUpdate()) {
            // 1.Fetch the advertisement set using the unique Identifier
            List<UserAdvertisement> userAdvertisementList = userAdvertisementRepository.getAdsListByUniqueIdentifier(uploadAdReq.getUniqueIdentifier());
            if (!userAdvertisementList.get(0).getAdvertisement().getAdvertisementUrl().equals(uploadAdReq.getUrl())) {
                // 2. If the advertisement url is different from the existing advertisement url, then update the advertisement url
                Advertisement updatedAdvertisement = userAdvertisementList.get(0).getAdvertisement();
                updatedAdvertisement.setAdvertisementUrl(uploadAdReq.getUrl());
                advertisement = advertisementRepository.save(updatedAdvertisement);
                isUrlUpdated = true;
            }

            for (BasicAdDetails basicAdDetails : uploadAdReq.getPriorityList()) {
                for (UserAdvertisement userAdvertisement : userAdvertisementList) {
                    userAdvertisement.setUser(user);
                    userAdvertisement.setBusinessCategory(getBusinessCategoryById(uploadAdReq.getBusCatId()));
                    userAdvertisement.setPriority(basicAdDetails.getPriority());
                    userAdvertisement.setTargetedAge(basicAdDetails.getAgeRange());
                    userAdvertisement.setTargetedAudience(basicAdDetails.getGender());
                    userAdvertisement.setStatus(Status.PENDING_STATUS.status());
                    userAdvertisement.setUniqueIdentifier(uploadAdReq.getUniqueIdentifier());
                    if (isUrlUpdated) {
                        userAdvertisement.setAdvertisement(advertisement);
                    }
                    userAdvertisementRepository.save(userAdvertisement);

                }
            }

            return "Advertisement updated successfully";
        } else {

            advertisement.setAdvertisementUrl(uploadAdReq.getUrl());
            Advertisement newAdvertisement = advertisementRepository.save(advertisement);
            if (newAdvertisement != null) {
                for (BasicAdDetails basicAdDetails : uploadAdReq.getPriorityList()) {
                    UserAdvertisement userAdvertisement = new UserAdvertisement();
                    userAdvertisement.setAdvertisement(newAdvertisement);
                    userAdvertisement.setUser(user);
                    userAdvertisement.setBusinessCategory(getBusinessCategoryById(uploadAdReq.getBusCatId()));
                    userAdvertisement.setPriority(basicAdDetails.getPriority());
                    userAdvertisement.setTargetedAge(basicAdDetails.getAgeRange());
                    userAdvertisement.setTargetedAudience(basicAdDetails.getGender());
                    userAdvertisement.setStatus(Status.PENDING_STATUS.status());
                    userAdvertisement.setUniqueIdentifier(uploadAdReq.getUniqueIdentifier());
                    userAdvertisementRepository.save(userAdvertisement);
                }
                return "Advertisement uploaded successfully";
            } else {
                return "Error while uploading";
            }
        }

    }

}
