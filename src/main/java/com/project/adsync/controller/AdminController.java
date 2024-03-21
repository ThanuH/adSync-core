package com.project.adsync.controller;

import com.project.adsync.domain.ReportedIssue;
import com.project.adsync.domain.User;
import com.project.adsync.model.AdsyncResponse;
import com.project.adsync.service.AdminService;
import com.project.adsync.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/adSync.api/admin")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://www.adsynclk.xyz")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @GetMapping("/getDashboardDetails")
    public AdsyncResponse getTotalNoOfUsers() {
        HashMap<String, Integer> dashBoardDetails = new HashMap<>();
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        dashBoardDetails = adminService.getDashboardDetails();
        adsyncResponse.setResponseCode("200");
        adsyncResponse.setResponseObject(dashBoardDetails);
        return adsyncResponse;
    }

    @GetMapping("/getAllUsers")
    public AdsyncResponse getPendingUsers(@RequestParam(value = "userName", required = false) String userName) {
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        if(userName != null){
            User user =   userService.getUserByUserName(userName);
            if (user != null) {
                adsyncResponse.setResponseCode("200");
                adsyncResponse.setResponseObject(user);
                return adsyncResponse;
            }else {
                adsyncResponse.setResponseCode("404");
                adsyncResponse.setResponseObject("User not found");
                return adsyncResponse;
            }
        }else {
            List<User> pendingUsers = userService.getPendingUsers();
            adsyncResponse.setResponseCode("200");
            adsyncResponse.setResponseObject(pendingUsers);
            return adsyncResponse;
        }

    }

    @GetMapping("/getAllPendingReportedIssues")
    public AdsyncResponse getAllPendingReportedIssues(@RequestParam(value = "userName", required = false) String userName) {
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        if(userName != null){
            User user =   userService.getUserByUserName(userName);
            if (user != null) {
                List<ReportedIssue> userWiseIssues = userService.getUserWiseIssues(user);
                adsyncResponse.setResponseCode("200");
                adsyncResponse.setResponseObject(userWiseIssues);
            }else {
                List<ReportedIssue> reportedIssues = userService.getAllPendingReportedIssues();
                adsyncResponse.setResponseCode("200");
                adsyncResponse.setResponseObject(reportedIssues);
            }
        }

        return adsyncResponse;
    }

}
