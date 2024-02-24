package com.project.adsync.service;

import com.project.adsync.domain.User;
import com.project.adsync.model.request.LoginReq;
import com.project.adsync.model.request.UserRegReq;

public interface UserService {
    User registerUser(UserRegReq userRegReq);

    String loginUser(LoginReq loginReq);
}
