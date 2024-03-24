package com.project.adsync.service;

import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.ReportedIssue;
import com.project.adsync.domain.User;
import com.project.adsync.enums.AdsyncApplicationError;
import com.project.adsync.enums.Status;
import com.project.adsync.exception.AdsyncException;
import com.project.adsync.model.request.LoginReq;
import com.project.adsync.model.request.ReportIssueReq;
import com.project.adsync.model.request.UserRegReq;
import com.project.adsync.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    BusinessCategoryRepository businessCategoryRepository;

    @Autowired
    UserAdvertisementRepository userAdvertisementRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    ReportedIssueRepository reportedIssueRepository;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User registerUser(UserRegReq userRegReq) {
        Optional<User> user = userRepository.findUserByEmail(userRegReq.getEmail());
        String email = user.map(User::getEmail).orElse(null);
        logger.info("Email: " + email);
        if (email != null) {
            return null;
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
            newUser.setUserRole(userRoleRepository.getUserById(2));
            logger.info("User " + user.toString());
            return userRepository.save(newUser);
        }

    }

    @Override
    public User loginUser(LoginReq loginReq) {

        User user = userRepository.findByEmail(loginReq.getEmail());

        if (user != null) {
            String password = loginReq.getPassword();
            String password2 = user.getPassword();
            if (Objects.equals(password, password2)) {
                return user;
            }
            throw new AdsyncException(AdsyncApplicationError.INVALID_CREDENTIALS);
        }
        throw new AdsyncException(AdsyncApplicationError.INVALID_CREDENTIALS);
    }

    @Override
    public HashMap<String, Integer> getUserDashboardDetails(int userId) {
        User user = userRepository.getReferenceById(userId);
        HashMap<String, Integer> dashBoardDetails = new HashMap<>();
        if(user == null){
            throw new AdsyncException(AdsyncApplicationError.USER_NOT_FOUND);
        }else {
            int submittedAdCount = userAdvertisementRepository.getCustomerWiseSubmitedAdCount(user);
            dashBoardDetails.put("submittedAdCount", submittedAdCount);

            int approvedAdCount = userAdvertisementRepository.getCustomerWiseApprovedAdCount(user);
            dashBoardDetails.put("approvedAdCount",approvedAdCount);

            int rejectedAdCount = userAdvertisementRepository.getCustomerWiseRejectedAdCount(user);
            dashBoardDetails.put("rejectedAdCount",rejectedAdCount);

            int pendingAdCount = userAdvertisementRepository.getCustomerWisePendingAdCount(user);
            dashBoardDetails.put("pendingAdCount",pendingAdCount);

            return dashBoardDetails;

        }
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByEmail(userName);
    }

    @Override
    public List<User> getPendingUsers() {
        return userRepository.findAll();
    }

    @Override
    public String reportIssue(int id, ReportIssueReq issue) {
        User user = userRepository.getReferenceById(id);
        if(user == null){
            throw new AdsyncException(AdsyncApplicationError.USER_NOT_FOUND);
        }else {
            ReportedIssue reportedIssue = new ReportedIssue();
            reportedIssue.setUser(user);
            reportedIssue.setIssueDescription(issue.getIssueDescription());
            reportedIssue.setStatus(Status.PENDING_STATUS.status());
            reportedIssueRepository.save(reportedIssue);
            return "Issue reported successfully";
        }
    }

    @Override
    public List<ReportedIssue> getUserWiseIssues(User user) {
        return reportedIssueRepository.getUserWiseIssues(user);
    }

    @Override
    public List<ReportedIssue> getAllPendingReportedIssues() {
        return reportedIssueRepository.findAll();
    }

    @Override
    public void updateUserStatus(String status, User user) {
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.getReferenceById(userId);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
