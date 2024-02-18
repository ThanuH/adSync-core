package com.project.adsync.service;

import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.User;
import com.project.adsync.enums.ApplicationError;
import com.project.adsync.exception.AdsyncException;
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
        Optional<User> user = userRepository.findUserByEmail(userRegReq.getEmail());
        String email = user.map(User::getEmail).orElse(null);
        if (email != null) {
            throw new AdsyncException(ApplicationError.USER_ALREADY_EXISTS);
        } else {
            User newUser = new User();
            newUser.setEmail(userRegReq.getEmail());
            newUser.setContactNumber(userRegReq.getContactNo());
            newUser.setBusinessRegistrationNumber(userRegReq.getBusinessRegNo());
            newUser.setBusinessName(userRegReq.getBusinessName());
            Optional<BusinessCategory> businessCategory = businessCategoryRepository.findById(userRegReq.getBusinessCategory());
            newUser.setBusinessCategory(businessCategory.orElse(null));
            newUser.setPassword(userRegReq.getPassword());
            newUser.setStatus("A");

            return userRepository.save(newUser);
        }

    }
}
