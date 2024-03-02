package com.project.adsync.controller;

import com.project.adsync.model.AdsyncResponse;
import com.project.adsync.service.AdminService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/adSync.api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/getDashboardDetails")
    public AdsyncResponse getTotalNoOfUsers() {
        HashMap<String, Integer> dashBoardDetails = new HashMap<>();
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        dashBoardDetails = adminService.getDashboardDetails();
        adsyncResponse.setResponseCode("200");
        adsyncResponse.setResponseObject(dashBoardDetails);
        return adsyncResponse;
    }

}
