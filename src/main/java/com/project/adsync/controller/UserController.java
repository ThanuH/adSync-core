package com.project.adsync.controller;

import com.project.adsync.domain.User;
import com.project.adsync.model.AdsyncResponse;
import com.project.adsync.model.request.UserRegReq;

import com.project.adsync.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adSync.api/user")
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping(value ="/register",method = RequestMethod.POST)
    public AdsyncResponse register(@RequestBody UserRegReq userRegReq){
        User user = userService.registerUser(userRegReq);
        AdsyncResponse response = new AdsyncResponse();
        response.setResponseCode("00");
        response.setResponseObject(user);
        return response;
    }


}


