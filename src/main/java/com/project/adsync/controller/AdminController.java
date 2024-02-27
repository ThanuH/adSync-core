package com.project.adsync.controller;

import com.project.adsync.model.AdsyncResponse;
import com.project.adsync.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/adSync.api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("/getDashboardDetails")
    public AdsyncResponse getTotalNoOfUsers() {
        HashMap<String, Integer> dashBoardDetails = new HashMap<>();
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        dashBoardDetails = adminService.getDashboardDetails();
        adsyncResponse.setResponseObject(dashBoardDetails);
        return adsyncResponse;
    }

}
