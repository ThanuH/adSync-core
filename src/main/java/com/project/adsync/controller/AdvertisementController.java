package com.project.adsync.controller;

import com.project.adsync.domain.*;
import com.project.adsync.enums.AdsyncApplicationError;
import com.project.adsync.exception.AdsyncException;
import com.project.adsync.model.AdsyncResponse;
import com.project.adsync.model.request.UploadAdReq;
import com.project.adsync.repository.UserRepository;
import com.project.adsync.service.AdvertisementService;
import com.project.adsync.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adSync.api/advertisement")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://www.adsynclk.xyz, maxAge = 3600")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping(value = "/getAllBusinessCatergories")
    public List<BusinessCategory> getAllBusinessCategories() {
        List<BusinessCategory> businessCategories = advertisementService.getBussinessCategories();
        return businessCategories;
    }

    @PostMapping(value = "/uploadAd")
    public AdsyncResponse uploadUserAdvertisement(@RequestBody UploadAdReq uploadAdReq) {
        User user = userRepository.getUserById(uploadAdReq.getUserId());
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        if (user != null) {
            if (uploadAdReq.getUrl() != null) {
                String response = advertisementService.uploadUserAdvertisement(uploadAdReq, user);
                adsyncResponse.setResponseCode("200");
                adsyncResponse.setResponseObject(response);
                return adsyncResponse;
            } else {
                throw new AdsyncException(AdsyncApplicationError.MEDIA_NOT_FOUND);
            }
        } else {
            throw new AdsyncException(AdsyncApplicationError.USER_NOT_FOUND);
        }
    }

    @PostMapping(value = "/analyzeDemographicData")
    public ResponseEntity<ByteArrayResource> analyzeDemographicData(@RequestBody List<DemographicData> demographicDataList) {
        return advertisementService.analyzeDemographicData(demographicDataList);
    }

    @GetMapping(value = "/getAllPendingAdvertisement")
    public AdsyncResponse getAllPendingReportedIssues(@RequestParam(value = "userName", required = false) String userName) {
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        if (userName != null) {
            User user = userService.getUserByUserName(userName);
            if (user != null) {
                List<UserAdvertisement> userWisePendingAdvertisement = advertisementService.getUserWisePendingAdvertisement(user);
                adsyncResponse.setResponseCode("200");
                adsyncResponse.setResponseObject(userWisePendingAdvertisement);
            } else {
                adsyncResponse.setResponseCode("404");
                adsyncResponse.setResponseObject("User not found");
            }
        } else {
            List<UserAdvertisement> allPendingAdvertisement = advertisementService.getAllPendingAdvertisement();
            adsyncResponse.setResponseCode("200");
            adsyncResponse.setResponseObject(allPendingAdvertisement);
        }
        return adsyncResponse;

    }

    @PutMapping(value = "/{id}/updateAdStatus")
    public AdsyncResponse updateAdStatus(@PathVariable("uniquieIdentifier") String uniquieIdentifier, @RequestParam("status") String status) {
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        List<UserAdvertisement> uniqueUserAdvertisements = advertisementService.getAdByUniqueIdentifier(uniquieIdentifier);
        if (!uniqueUserAdvertisements.isEmpty()) {
            advertisementService.updateAdStatus(status, uniqueUserAdvertisements);
            adsyncResponse.setResponseCode("200");
            adsyncResponse.setResponseObject("Advertisement Updated Successfully !");
        } else {
            adsyncResponse.setResponseCode("404");
            adsyncResponse.setResponseObject("Invalid unique identifier. Please recheck !");
        }
        return adsyncResponse;
    }

    @GetMapping(value = "/{id}/getAdDetails")
    public AdsyncResponse getAdDetails(@PathVariable("id") int id) {
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        UserAdvertisement userAdvertisement = advertisementService.getUserAdvertisementById(id);
        if (userAdvertisement != null) {
            adsyncResponse.setResponseCode("200");
            adsyncResponse.setResponseObject(userAdvertisement);
        } else {
            adsyncResponse.setResponseCode("404");
            adsyncResponse.setResponseObject("Advertisement not found");
        }
        return adsyncResponse;
    }

    @GetMapping(value = "/{id}/getAdByUser")
    public AdsyncResponse getAdByUser(@PathVariable("id") int id) {
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        List<UserAdvertisement> userAdvertisements = advertisementService.getAdByUser(id);
        if (!userAdvertisements.isEmpty()) {
            adsyncResponse.setResponseCode("200");
            adsyncResponse.setResponseObject(userAdvertisements);
        } else {
            adsyncResponse.setResponseCode("404");
            adsyncResponse.setResponseObject("Advertisement not found");
        }
        return adsyncResponse;
    }
}
