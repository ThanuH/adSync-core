package com.project.adsync.repository;

import com.project.adsync.domain.BusinessCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessCategoryRepository extends JpaRepository<BusinessCategory, Integer> {
}
