package com.project.adsync.controller;

import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adSync.api/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;



    @RequestMapping(value = "/getAllBusinessCatergories", method = RequestMethod.GET)
    public List<BusinessCategory> getAllBusinessCategories() {
        List<BusinessCategory> businessCategories = advertisementService.getBussinessCategories();
        return businessCategories;
    }
}
