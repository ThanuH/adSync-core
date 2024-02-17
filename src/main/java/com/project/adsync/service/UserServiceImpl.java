package com.project.adsync.service;

import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.User;
import com.project.adsync.model.request.UserRegReq;
import com.project.adsync.repository.BusinessCategoryRepository;
import com.project.adsync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    BusinessCategoryRepository businessCategoryRepository;

    @Override
    public User registerUser(UserRegReq userRegReq) {
        User user = new User();
        user.setEmail(userRegReq.getEmail());
        user.setContactNumber(userRegReq.getContactNo());
        user.setBusinessRegistrationNumber(userRegReq.getBusinessRegNo());
        user.setBusinessName(userRegReq.getBusinessName());

        Optional<BusinessCategory> businessCategory = businessCategoryRepository.findById(1);
        user.setBusinessCategory(businessCategory.orElse(null));
        user.setPassword(userRegReq.getPassword());
        user.setStatus("A");

        return userRepository.save(user);
    }
}
