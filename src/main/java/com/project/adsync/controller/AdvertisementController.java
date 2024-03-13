package com.project.adsync.controller;

import com.project.adsync.domain.Advertisement;
import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.User;
import com.project.adsync.domain.UserAdvertisement;
import com.project.adsync.enums.AdsyncApplicationError;
import com.project.adsync.exception.AdsyncException;
import com.project.adsync.model.AdsyncResponse;
import com.project.adsync.model.request.UploadAdReq;
import com.project.adsync.repository.AdvertisemntRepository;
import com.project.adsync.repository.UserAdvertisementRepository;
import com.project.adsync.repository.UserRepository;
import com.project.adsync.service.AdvertisementService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adSync.api/advertisement")
@CrossOrigin(origins = "http://localhost:3000")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAdvertisementRepository userAdvertisementRepository;



    @GetMapping (value = "/getAllBusinessCatergories")
    public List<BusinessCategory> getAllBusinessCategories() {
        List<BusinessCategory> businessCategories = advertisementService.getBussinessCategories();
        return businessCategories;
    }

    @PostMapping(value = "/uploadAd")
    public AdsyncResponse uploadUserAdvertisement(@RequestBody UploadAdReq uploadAdReq){
        User user = userRepository.getUserById(uploadAdReq.getUserId());
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        if (user != null){
            if (uploadAdReq.getUrl() != null){
                String response = advertisementService.uploadUserAdvertisement(uploadAdReq,user);
                adsyncResponse.setResponseCode("200");
                adsyncResponse.setResponseObject(response);
                return adsyncResponse;
            }else{
                throw new AdsyncException(AdsyncApplicationError.MEDIA_NOT_FOUND);
            }
        }else{
            throw new AdsyncException(AdsyncApplicationError.USER_NOT_FOUND);
        }
    }




}
