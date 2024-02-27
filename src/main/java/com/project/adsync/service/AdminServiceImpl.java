package com.project.adsync.service;

import com.project.adsync.domain.User;
import com.project.adsync.domain.UserRole;
import com.project.adsync.repository.AdvertisementRepository;
import com.project.adsync.repository.UserRepository;
import com.project.adsync.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Override
    public int getTotalNoOfUsers(int id) {
        UserRole userRole = userRoleRepository.getUserById(id);
        return userRepository.getUserCountByType(userRole);
    }

    @Override
    public HashMap<String, Integer> getDashboardDetails() {
        //Since user role's id is 2 it's hardcoded within the code
        int userRoleId = 2;
        HashMap<String, Integer> dashBoardDetails = new HashMap<>();
        int totalNoOfUsers = getTotalNoOfUsers(userRoleId);
        dashBoardDetails.put("totalNoOfUsers", totalNoOfUsers);

        int totalNoOfSubmissions = advertisementRepository.getSubmittedCount();
        dashBoardDetails.put("totalNoOfSubmissions", totalNoOfSubmissions);

        int totalNoOfApproved = advertisementRepository.getApprovedCount();
        dashBoardDetails.put("totalNoOfApproved", totalNoOfApproved);

        int totalNoOfRejected = advertisementRepository.getRejectedCount();
        dashBoardDetails.put("totalNoOfRejected", totalNoOfRejected);

        return dashBoardDetails;
    }

    /*@Override
    public int getTotalNoOfUsers() {
        User user =  userRepository.
        return userRepository.getUserCountByType(users.get().getUserRole());
    }*/


}
