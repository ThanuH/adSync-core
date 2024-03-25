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
@CrossOrigin(origins = "http://www.adsynclk.xyz, maxAge = 3600")
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
        if (userName != null) {
            User user = userService.getUserByUserName(userName);
            if (user != null) {
                List<ReportedIssue> userWiseIssues = userService.getUserWiseIssues(user);
                adsyncResponse.setResponseCode("200");
                adsyncResponse.setResponseObject(userWiseIssues);
            } else {
                adsyncResponse.setResponseCode("404");
                adsyncResponse.setResponseObject("User not found");
            }
        } else {
            List<ReportedIssue> reportedIssues = userService.getAllPendingReportedIssues();
            adsyncResponse.setResponseCode("200");
            adsyncResponse.setResponseObject(reportedIssues);
        }
        return adsyncResponse;
    }

    @PutMapping("/{userId}/updateUserStatus")
    public AdsyncResponse updateUserStatus(@PathVariable("userId") int userId, @RequestParam("status") String status) {
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        User user = userService.getUserById(userId);
        if (user != null) {
            userService.updateUserStatus(status, user);
            adsyncResponse.setResponseCode("200");
            adsyncResponse.setResponseObject("User status updated successfully");
        } else {
            adsyncResponse.setResponseCode("404");
            adsyncResponse.setResponseObject("User not found");
        }
        return adsyncResponse;
    }

    @DeleteMapping("/{userId}/deleteUser")
    public AdsyncResponse deleteUser(@PathVariable("userId") int userId) {
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        User user = userService.getUserById(userId);
        if (user != null) {
            userService.deleteUser(user);
            adsyncResponse.setResponseCode("200");
            adsyncResponse.setResponseObject("User deleted successfully");
        } else {
            adsyncResponse.setResponseCode("404");
            adsyncResponse.setResponseObject("User not found");
        }
        return adsyncResponse;
    }

    @PutMapping("/{issueId}/updateIssueStatus")
    public AdsyncResponse updateIssueStatus(@PathVariable("issueId") int issueId, @RequestParam("status") String status) {
        AdsyncResponse adsyncResponse = new AdsyncResponse();
        ReportedIssue reportedIssueById = adminService.getReportedIssueById(issueId);
        if (reportedIssueById != null) {
            adminService.updateIssueStatus(status, reportedIssueById);
            adsyncResponse.setResponseCode("200");
            adsyncResponse.setResponseObject("Issue status updated successfully!");
        } else {
            adsyncResponse.setResponseCode("404");
            adsyncResponse.setResponseObject("Issue not found!");
        }
        return adsyncResponse;
    }

}
