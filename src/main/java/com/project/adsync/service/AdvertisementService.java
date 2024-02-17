package com.project.adsync.service;

import com.project.adsync.domain.BusinessCategory;

import java.util.List;

public interface AdvertisementService {

    List<BusinessCategory> getBussinessCategories();

    BusinessCategory getBusinessCategoryById(int id);
}
