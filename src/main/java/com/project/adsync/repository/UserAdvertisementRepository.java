package com.project.adsync.repository;

import com.project.adsync.domain.Advertisement;
import com.project.adsync.domain.ReportedIssue;
import com.project.adsync.domain.User;
import com.project.adsync.domain.UserAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAdvertisementRepository extends JpaRepository<UserAdvertisement, Integer> {
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'PENDING'")
    int getSubmittedCount();
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'APPROVED'")
    int getApprovedCount();
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'REJECTED'")
    int getRejectedCount();
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE  a.user = :user")
    int getCustomerWiseSubmitedAdCount(@Param("user") User user);
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'APPROVED' AND a.user = :user")
    int getCustomerWiseApprovedAdCount(@Param("user") User user);
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'REJECTED' AND a.user = :user")
    int getCustomerWiseRejectedAdCount(@Param("user") User user);
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'PENDING' AND a.user = :user")
    int getCustomerWisePendingAdCount(@Param("user") User user);
    @Query("SELECT ua FROM UserAdvertisement ua WHERE ua.uniqueIdentifier = :uniqueIdentifier")
    List<UserAdvertisement> getAdsListByUniqueIdentifier(@Param("uniqueIdentifier") String uniqueIdentifier);
    @Query("SELECT ua FROM UserAdvertisement ua WHERE ua.targetedAge = :ageRange AND ua.targetedAudience = :gender AND ua.priority = :priority AND ua.status = :status")
    List<UserAdvertisement> findMatchingAdsByDemographicAndPriority(
            @Param("ageRange") String ageRange,
            @Param("gender") String gender,
            @Param("priority") int priority,
            @Param("status") String status
    );
    @Query("DELETE FROM UserAdvertisement ua WHERE ua.uniqueIdentifier = :uniqueIdentifier")
    void deleteByUniqueIdentifier(@Param("uniqueIdentifier") String uniqueIdentifier);

    @Query("SELECT ua FROM UserAdvertisement ua WHERE ua.status = 'PENDING' AND ua.user = :user")
    List<UserAdvertisement> getUserWisePendingAdvertisement(User user);

    @Query("SELECT ua FROM UserAdvertisement ua WHERE ua.status = 'PENDING'")
    List<UserAdvertisement> findAllPendingAds();

}