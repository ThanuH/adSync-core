package com.project.adsync.service;

import com.project.adsync.domain.User;

import java.util.HashMap;
import java.util.Optional;

public interface AdminService {
    int getTotalNoOfUsers(int id);
    HashMap<String, Integer> getDashboardDetails();
}
