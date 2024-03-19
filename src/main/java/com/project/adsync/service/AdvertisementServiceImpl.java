package com.project.adsync.service;

import com.project.adsync.domain.*;
import com.project.adsync.enums.Status;
import com.project.adsync.model.BasicAdDetails;
import com.project.adsync.model.request.UploadAdReq;
import com.project.adsync.repository.AdvertisemntRepository;
import com.project.adsync.repository.BusinessCategoryRepository;
import com.project.adsync.repository.UserAdvertisementRepository;
import com.project.adsync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdvertisementServiceImpl implements AdvertisementService{
    @Autowired
    private BusinessCategoryRepository businessCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAdvertisementRepository userAdvertisementRepository;

    @Autowired
    private AdvertisemntRepository advertisementRepository;

    @Override
    public List<BusinessCategory> getBussinessCategories() {
        return  businessCategoryRepository.findAll();
    }

    @Override
    public BusinessCategory getBusinessCategoryById(int id) {
        Optional<BusinessCategory> businessCategory = businessCategoryRepository.findById(id);
        return businessCategory.orElse(null);
    }

    @Override
    public String uploadUserAdvertisement(UploadAdReq uploadAdReq, User user) {
        Advertisement advertisement = new Advertisement();
        boolean isUrlUpdated = false;
        if (uploadAdReq.isUpdate()) {
            // 1.Fetch the advertisement set using the unique Identifier
            List<UserAdvertisement> userAdvertisementList = userAdvertisementRepository.getAdsListByUniqueIdentifier(uploadAdReq.getUniqueIdentifier());
            if (!userAdvertisementList.get(0).getAdvertisement().getAdvertisementUrl().equals(uploadAdReq.getUrl())) {
                // 2. If the advertisement url is different from the existing advertisement url, then update the advertisement url
                Advertisement updatedAdvertisement = userAdvertisementList.get(0).getAdvertisement();
                updatedAdvertisement.setAdvertisementUrl(uploadAdReq.getUrl());
                advertisement = advertisementRepository.save(updatedAdvertisement);
                isUrlUpdated = true;
            }

            for (BasicAdDetails basicAdDetails : uploadAdReq.getPriorityList()) {
                for (UserAdvertisement userAdvertisement : userAdvertisementList) {
                    userAdvertisement.setUser(user);
                    userAdvertisement.setBusinessCategory(getBusinessCategoryById(uploadAdReq.getBusCatId()));
                    userAdvertisement.setPriority(basicAdDetails.getPriority());
                    userAdvertisement.setTargetedAge(basicAdDetails.getAgeRange());
                    userAdvertisement.setTargetedAudience(basicAdDetails.getGender());
                    userAdvertisement.setStatus(Status.PENDING_STATUS.status());
                    userAdvertisement.setUniqueIdentifier(uploadAdReq.getUniqueIdentifier());
                    if (isUrlUpdated) {
                        userAdvertisement.setAdvertisement(advertisement);
                    }
                    userAdvertisementRepository.save(userAdvertisement);

                }
            }

            return "Advertisement updated successfully";
        } else {

            advertisement.setAdvertisementUrl(uploadAdReq.getUrl());
            Advertisement newAdvertisement = advertisementRepository.save(advertisement);
            if (newAdvertisement != null) {
                for (BasicAdDetails basicAdDetails : uploadAdReq.getPriorityList()) {
                    UserAdvertisement userAdvertisement = new UserAdvertisement();
                    userAdvertisement.setAdvertisement(newAdvertisement);
                    userAdvertisement.setUser(user);
                    userAdvertisement.setBusinessCategory(getBusinessCategoryById(uploadAdReq.getBusCatId()));
                    userAdvertisement.setPriority(basicAdDetails.getPriority());
                    userAdvertisement.setTargetedAge(basicAdDetails.getAgeRange());
                    userAdvertisement.setTargetedAudience(basicAdDetails.getGender());
                    userAdvertisement.setStatus(Status.PENDING_STATUS.status());
                    userAdvertisement.setUniqueIdentifier(uploadAdReq.getUniqueIdentifier());
                    userAdvertisementRepository.save(userAdvertisement);
                }
                return "Advertisement uploaded successfully";
            } else {
                return "Error while uploading";
            }
        }

    }

    @Override
    public Advertisement analyzeDemographicData(List<DemographicData> demographicDataList) {
        Advertisement toDisplay;
        Random rand = new Random();
        int totalApprovedAds = userAdvertisementRepository.getApprovedCount();

        if (demographicDataList.isEmpty()) {    // If no demographic data is provided or no audience present, display a random ad
            int randIndex = rand.nextInt(totalApprovedAds);
            toDisplay = userAdvertisementRepository.findAll().get(randIndex).getAdvertisement();
        }
        else {    // If demographic data is provided, display an ad based on the demographic data
            int randIndex = rand.nextInt(totalApprovedAds);
            toDisplay = userAdvertisementRepository.findAll().get(randIndex).getAdvertisement();    // Display a random ad if no matching ad is found

            Map<String, Integer> categoryCounts = new HashMap<>();
            Map<String, Integer> topCategories = new HashMap<>();

            for (DemographicData demographicData : demographicDataList) { // Count the number of people in each category
                String category = demographicData.getAgeRange() + "-" + demographicData.getGender();
                categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
            }

            categoryCounts.entrySet().stream() // Sort the categories by the number of people in each category
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(2)
                    .forEach(entry -> topCategories.put(entry.getKey(), entry.getValue())); // Get the top 2 categories

            outerLoop:
            for (Map.Entry<String, Integer> entry : topCategories.entrySet()) { // For each of the top 2 categories
                String takenRange = entry.getKey().substring(0, 5);
                String takenGender = entry.getKey().substring(6);

                for (int checkingPriority = 1; checkingPriority <= 3; checkingPriority++) { // Check for ads with priority 1, 2, and 3
                    List<UserAdvertisement> matchingAds = userAdvertisementRepository.findMatchingAdsByDemographicAndPriority(takenRange, takenGender, checkingPriority, "A");
                    if (!matchingAds.isEmpty()) {   // If matching ads are found, display a random ad from the matching ads for specific gender
                        randIndex = rand.nextInt(matchingAds.size());
                        toDisplay = matchingAds.get(randIndex).getAdvertisement();
                        break outerLoop;
                    }

                    List<UserAdvertisement> matchingAds2 = userAdvertisementRepository.findMatchingAdsByDemographicAndPriority(takenRange, "Both", checkingPriority, "A");
                    if (!matchingAds2.isEmpty()) {  // If matching ads are found, display a random ad from the matching ads for gender="Both"
                        randIndex = rand.nextInt(matchingAds2.size());
                        toDisplay = matchingAds2.get(randIndex).getAdvertisement();
                        break outerLoop;
                    }
                }
            }
        }
        return toDisplay;
    }

//    @Override
//    public Advertisement analyzeDemographicData(List<DemographicData> demographicDataList) {
//        Advertisement toDisplay = new Advertisement();
//        Map<String, Integer> categoryCounts = new HashMap<>();
//        Map<String, Integer> topCategories = new HashMap<>();
//        Random rand = new Random();
//        int randInt = rand.nextInt(userAdvertisementRepository.getApprovedCount());
//
//        if (demographicDataList.isEmpty()) {
//            toDisplay = userAdvertisementRepository.findAll().get(randInt).getAdvertisement();
//        } else {
//            toDisplay = userAdvertisementRepository.findAll().get(randInt).getAdvertisement();
//
//            for (DemographicData demographicData : demographicDataList) {
//                String category = demographicData.getAgeRange() + "-" + demographicData.getGender();
//                categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
//            }
//            categoryCounts.entrySet().stream()
//                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
//                    .limit(2)
//                    .forEach(entry -> topCategories.put(entry.getKey(), entry.getValue()));
//
//            Outerloop:
//            for ( Map.Entry<String, Integer> entry : topCategories.entrySet()) {
//                String takenRange = entry.getKey().substring(0,5);
//                String takenGender = entry.getKey().substring(6);
//                int peopleCount = entry.getValue();
//                int checkingPriority;
//
//                for (checkingPriority = 1; checkingPriority <= 3 ; checkingPriority++) {
//                    List<UserAdvertisement> matchingAds = userAdvertisementRepository.findMatchingAdsByDemographicAndPriority(takenRange, takenGender, checkingPriority, "A");
//                    List<UserAdvertisement> matchingAds2 = userAdvertisementRepository.findMatchingAdsByDemographicAndPriority(takenRange, "Both", checkingPriority, "A");
//
//                    if (!matchingAds.isEmpty()) {
//                        randInt = rand.nextInt( matchingAds.size() );
//                        toDisplay=(matchingAds.get(randInt).getAdvertisement());
//                        break Outerloop;
//                    } else if (!matchingAds2.isEmpty()) {
//                        randInt = rand.nextInt( matchingAds2.size() );
//                        toDisplay=(matchingAds2.get(randInt).getAdvertisement());
//                        break Outerloop;
//                    }
//                }
//            }
//        }
//        return toDisplay;
//    }

//    Body for POSTMAN testing
//            [
//    {
//        "ageRange": "18-24",
//            "gender": "Male"
//    },
//    {
//        "ageRange": "18-24",
//            "gender": "Male"
//    },
//    {
//        "ageRange": "25-34",
//            "gender": "Female"
//    },
//    {
//        "ageRange": "35-44",
//            "gender": "Male"
//    },
//    {
//        "ageRange": "18-24",
//            "gender": "Female"
//    },
//    {
//        "ageRange": "18-24",
//            "gender": "Female"
//    },
//    {
//        "ageRange": "18-24",
//            "gender": "Female"
//    }
//]

}
