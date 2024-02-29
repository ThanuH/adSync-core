package com.project.adsync.controller;

import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.User;
import com.project.adsync.enums.AdsyncApplicationError;
import com.project.adsync.exception.AdsyncException;
import com.project.adsync.model.AdsyncResponse;
import com.project.adsync.model.request.UploadAdReq;
import com.project.adsync.repository.UserRepository;
import com.project.adsync.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adSync.api/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private UserRepository userRepository;



    @GetMapping (value = "/getAllBusinessCatergories")
    public List<BusinessCategory> getAllBusinessCategories() {
        List<BusinessCategory> businessCategories = advertisementService.getBussinessCategories();
        return businessCategories;
    }

    @PostMapping(value = "/uploadAd")
    public AdsyncResponse uploadUserAdvertisement(@RequestBody UploadAdReq uploadAdReq){
        User user = userRepository.getUserById(uploadAdReq.getUserId());
        if (user != null){
            if (uploadAdReq.getUrl() != null){
                String response = advertisementService.uploadUserAdvertisement(uploadAdReq,user);
            }else{
                throw new AdsyncException(AdsyncApplicationError.MEDIA_NOT_FOUND);
            }
        }else{
            throw new AdsyncException(AdsyncApplicationError.USER_NOT_FOUND);
        }
        return null;
    }
}
