package com.project.adsync.controller;

import com.project.adsync.domain.User;
import com.project.adsync.model.AdsyncResponse;
import com.project.adsync.model.request.LoginReq;
import com.project.adsync.model.request.UserRegReq;

import com.project.adsync.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/login")
    public AdsyncResponse login(@RequestBody LoginReq loginReq)
    {
        String loginMessage = userService.loginUser(loginReq);
        AdsyncResponse response = new AdsyncResponse();
        response.setResponseCode("00");
        response.setResponseObject(loginMessage);
        return response;
    }
}


