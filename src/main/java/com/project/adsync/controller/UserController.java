package com.project.adsync.controller;

import com.project.adsync.domain.ReportedIssue;
import com.project.adsync.domain.User;
import com.project.adsync.enums.AdsyncApplicationError;
import com.project.adsync.enums.Status;
import com.project.adsync.exception.AdsyncException;
import com.project.adsync.model.AdsyncResponse;
import com.project.adsync.model.request.LoginReq;
import com.project.adsync.model.request.ReportIssueReq;
import com.project.adsync.model.request.UserRegReq;

import com.project.adsync.model.response.CustomerLoginResponse;
import com.project.adsync.service.AdvertisementService;
import com.project.adsync.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/adSync.api/user")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://www.adsynclk.xyz", maxAge = 3600)
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AdvertisementService advertisementService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public AdsyncResponse register(@RequestBody UserRegReq userRegReq) throws Exception {
        User user = userService.registerUser(userRegReq);
        AdsyncResponse response = new AdsyncResponse();
        if (user != null) {
            response.setResponseCode("200");
            response.setResponseObject(user);
        } else {
            throw new AdsyncException(AdsyncApplicationError.USER_ALREADY_EXIST);
        }
        return response;
    }

    @PostMapping(value = "/login")
    public AdsyncResponse login(@RequestBody LoginReq loginReq) {
        if (loginReq.getEmail() == null || loginReq.getPassword() == null) {
            throw new AdsyncException(AdsyncApplicationError.INVALID_CREDENTIALS);
        } else {
            AdsyncResponse response = new AdsyncResponse();
            User user = userService.loginUser(loginReq);
            if(user.getStatus().equals(Status.BLOCKED_STATUS.status())){
                throw new AdsyncException(AdsyncApplicationError.USER_BLOCKED);
            }else{
                response.setResponseCode("200");
                CustomerLoginResponse customerLoginResponse = new CustomerLoginResponse();
                customerLoginResponse.setCustomerId(user.getId());
                customerLoginResponse.setCustomerName(user.getBusinessName());
                customerLoginResponse.setCustomerEmail(user.getEmail());
                customerLoginResponse.setUserRole(user.getUserRole());
                response.setResponseObject(customerLoginResponse);
                return response;
            }


        }
    }

    @GetMapping(value = "/{userId}/getUserDashboardDetails")
    public AdsyncResponse getUserDashboardDetails(@PathVariable("userId") int userId) {
        AdsyncResponse response = new AdsyncResponse();
        HashMap<String, Integer> dashBoardDetails = new HashMap<>();
        dashBoardDetails = userService.getUserDashboardDetails(userId);
        response.setResponseCode("200");
        response.setResponseObject(dashBoardDetails);
        return response;
    }

    @PostMapping(value = "/{id}/reportIssue")
    public AdsyncResponse reportIssue(@PathVariable("id") int id, @RequestBody ReportIssueReq issue) {
        AdsyncResponse response = new AdsyncResponse();
        String message  = userService.reportIssue(id, issue);
        response.setResponseCode("200");
        response.setResponseObject("Issue reported successfully");
        return response;
    }

    @DeleteMapping(value = "/{userId}/userAd/{adId}")
    public AdsyncResponse deleteAd(@PathVariable("userId") int id, @PathVariable("adId") int adId) {
        AdsyncResponse response = new AdsyncResponse();
        String message  = advertisementService.deleteAd(id, adId);
        response.setResponseCode("200");
        response.setResponseObject(message);
        return response;
    }



}