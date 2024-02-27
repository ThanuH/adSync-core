package com.project.adsync.repository;

import com.project.adsync.domain.Advertisement;
import com.project.adsync.domain.UserAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<UserAdvertisement, Integer> {
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'S'")
    int getSubmittedCount();
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'A'")
    int getApprovedCount();
    @Query("SELECT count(*) FROM UserAdvertisement a WHERE a.status = 'R'")
    int getRejectedCount();
}
