package com.project.adsync.service;

import com.project.adsync.domain.User;
import com.project.adsync.model.request.LoginReq;
import com.project.adsync.model.request.UserRegReq;

import java.util.HashMap;

public interface UserService {
    User registerUser(UserRegReq userRegReq);

    User loginUser(LoginReq loginReq);

    HashMap<String, Integer> getUserDashboardDetails(int userId);
}
