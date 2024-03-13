package com.project.adsync.repository;

import com.project.adsync.domain.Advertisement;
import com.project.adsync.domain.User;
import com.project.adsync.domain.UserAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAdvertisementRepository extends JpaRepository<UserAdvertisement, Integer> {
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'S'")
    int getSubmittedCount();
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'A'")
    int getApprovedCount();
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'R'")
    int getRejectedCount();
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE  a.user = :user")
    int getCustomerWiseSubmitedAdCount(User user);
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'A' AND a.user = :user")
    int getCustomerWiseApprovedAdCount(User user);
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'R' AND a.user = :user")
    int getCustomerWiseRejectedAdCount(User user);
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'S' AND a.user = :user")
    int getCustomerWisePendingAdCount(User user);
//    @Query("SELECT ua FROM UserAdvertisement ua WHERE ua.uniqueIdentifier = :uniqueIdentifier")
//    List<UserAdvertisement> getAdsListByUniqueIdentifier(String uniqueIdentifier);
    @Query("SELECT ua FROM UserAdvertisement ua WHERE ua.user = :user")
    List<UserAdvertisement> getUserAdvertisementByUser(User user);
}
