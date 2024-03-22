package com.project.adsync.service;

import com.project.adsync.domain.ReportedIssue;
import com.project.adsync.domain.User;
import com.project.adsync.model.request.LoginReq;
import com.project.adsync.model.request.ReportIssueReq;
import com.project.adsync.model.request.UserRegReq;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    User registerUser(UserRegReq userRegReq);

    User loginUser(LoginReq loginReq);

    HashMap<String, Integer> getUserDashboardDetails(int userId);

    User getUserByUserName(String userName);

    List<User> getPendingUsers();

    String reportIssue(int id, ReportIssueReq issue);

    List<ReportedIssue> getUserWiseIssues(User user);

    List<ReportedIssue> getAllPendingReportedIssues();
}
