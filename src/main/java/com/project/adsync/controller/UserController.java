package com.project.adsync.controller;

import com.project.adsync.domain.User;
import com.project.adsync.enums.AdsyncApplicationError;
import com.project.adsync.exception.AdsyncException;
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
                response.setResponseCode("200");
                String message = "Login Successful";
                response.setResponseObject(message);
                return response;

        }
    }

}


