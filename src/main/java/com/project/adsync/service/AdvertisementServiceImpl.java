package com.project.adsync.service;

import com.project.adsync.domain.Advertisement;
import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.User;
import com.project.adsync.domain.UserAdvertisement;
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
    private AdvertisemntRepository advertisemntRepository;

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
        advertisement.setAdvertisementUrl(uploadAdReq.getUrl());
        Advertisement newAdvertisement = advertisemntRepository.save(advertisement);

        if (newAdvertisement != null){
            for (BasicAdDetails basicAdDetails : uploadAdReq.getPriorityList()) {
                UserAdvertisement userAdvertisement = new UserAdvertisement();
                userAdvertisement.setAdvertisement(newAdvertisement);
                userAdvertisement.setUser(user);
                userAdvertisement.setBusinessCategory(getBusinessCategoryById(uploadAdReq.getBusCatId()));
                userAdvertisement.setPriority(basicAdDetails.getPriority());
                userAdvertisement.setTargetedAge(basicAdDetails.getAgeRange());
                userAdvertisement.setTargetedAge(basicAdDetails.getGender());
                userAdvertisement.setStatus("P");
                userAdvertisementRepository.save(userAdvertisement);
            }
            return "Advertisement uploaded successfully";
        }else{
            return "Error while uploading";
        }
    }

    @Override
    public String updateUserAdvertisement(UploadAdReq uploadAdReq) {
        UserAdvertisement oldUserAdvertisement = userAdvertisementRepository.getReferenceById(uploadAdReq.getUserAdId());

        return null;
    }

}
