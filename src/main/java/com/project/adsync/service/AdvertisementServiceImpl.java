package com.project.adsync.service;

import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.User;
import com.project.adsync.domain.UserAdvertisement;
import com.project.adsync.model.request.UploadAdReq;
import com.project.adsync.repository.BusinessCategoryRepository;
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
        UserAdvertisement userAdvertisement = new UserAdvertisement();
        userAdvertisement.setUser(user);
        return null;
    }

}
