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
        if (user != null && !uploadAdReq.isUpdate()){
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
//Previous One
//    @PutMapping(value = "/{adId}/updateAd/")
//    public AdsyncResponse updateUserAdvertisement(@PathParam ("adId") int adId, @RequestBody UploadAdReq uploadAdReq){
//        Optional<UserAdvertisement> userAdvertisement = userAdvertisementRepository.findById(adId);
//        if (userAdvertisement.isPresent() && uploadAdReq.isUpdate()){
//            String response = advertisementService.updateUserAdvertisement(uploadAdReq);
//        }else{
//            throw new AdsyncException(AdsyncApplicationError.MEDIA_NOT_FOUND);
//        }
//        return null;
//    }

    //New Method
//    @PutMapping(value = "/updateAd")
//    public AdsyncResponse updateUserAdvertisement(@RequestBody UploadAdReq uploadAdReq) {
//        String response = advertisementService.updateUserAdvertisement(uploadAdReq);
//        AdsyncResponse adsyncResponse = new AdsyncResponse();
//        adsyncResponse.setResponseCode("200");
//        adsyncResponse.setResponseObject(response);
//        return adsyncResponse;
//    }
}
