package com.project.adsync.service;

import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.User;
import com.project.adsync.model.request.LoginReq;
import com.project.adsync.model.request.UserRegReq;
import com.project.adsync.repository.BusinessCategoryRepository;
import com.project.adsync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

    @Override
    public String loginUser(LoginReq loginReq) {

        User user = userRepository.findByEmail(loginReq.getEmail());

        if (user != null)
        {
            String password = loginReq.getPassword();
            String password2 = user.getPassword();
            if (Objects.equals(password, password2))
            {
                Optional<User> user1 = userRepository.findByEmailAndPassword(loginReq.getEmail(),password2);
                if (user1.isPresent())
                {
                    return "Login Success";
                }
                else return "Login Failed!";
            }
            else return "Ooops....Password not match";
        }
        else return "Email not exists!Login Failed";
    }
}
