package com.project.adsync.service;

import com.project.adsync.domain.User;
import com.project.adsync.model.request.LoginReq;
import com.project.adsync.model.request.UserRegReq;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    User registerUser(UserRegReq userRegReq);

    User loginUser(LoginReq loginReq);

    HashMap<String, Integer> getUserDashboardDetails(int userId);

    User getUserByUserName(String userName);

    List<User> getPendingUsers();

    String updateUser(int userId, Boolean isBlocked);

    String deleteUser(int userId);
}
