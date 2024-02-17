package com.project.adsync.repository;

import com.project.adsync.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {
}
