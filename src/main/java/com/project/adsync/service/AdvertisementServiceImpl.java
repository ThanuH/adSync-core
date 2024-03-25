package com.project.adsync.service;

import com.project.adsync.domain.*;
import com.project.adsync.enums.AdsyncApplicationError;
import com.project.adsync.enums.Status;
import com.project.adsync.exception.AdsyncException;
import com.project.adsync.model.BasicAdDetails;
import com.project.adsync.model.request.UploadAdReq;
import com.project.adsync.repository.AdvertisemntRepository;
import com.project.adsync.repository.BusinessCategoryRepository;
import com.project.adsync.repository.UserAdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private BusinessCategoryRepository businessCategoryRepository;

    @Autowired
    private UserAdvertisementRepository userAdvertisementRepository;

    @Autowired
    private AdvertisemntRepository advertisementRepository;

    @Autowired
    private CloudStorageService cloudStorageService;

    @Override
    public List<BusinessCategory> getBussinessCategories() {
        return businessCategoryRepository.findAll();
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
                    //Teenagers (08-18) , Adults (19-60) , Senior Citizens (61-100)
                    if(basicAdDetails.getAgeRange().equalsIgnoreCase("Child")) {
                        userAdvertisement.setTargetedAge("08-18");
                    } else if(basicAdDetails.getAgeRange().equalsIgnoreCase("Young")) {
                        userAdvertisement.setTargetedAge("19-60");
                    } else {
                        userAdvertisement.setTargetedAge("61-100");
                    }
                    //userAdvertisement.setTargetedAge(basicAdDetails.getAgeRange());
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
                    //Teenagers (08-18) , Adults (19-60) , Senior Citizens (61-100)
                    if(basicAdDetails.getAgeRange().equalsIgnoreCase("Child")) {
                        userAdvertisement.setTargetedAge("08-18");
                    } else if(basicAdDetails.getAgeRange().equalsIgnoreCase("Young")) {
                        userAdvertisement.setTargetedAge("19-60");
                    } else {
                        userAdvertisement.setTargetedAge("61-100");
                    }
                    //userAdvertisement.setTargetedAge(basicAdDetails.getAgeRange());
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
    public ResponseEntity<ByteArrayResource> analyzeDemographicData(List<DemographicData> demographicDataList) {
        Advertisement toDisplay;
        Random rand = new Random();
        int randInt = rand.nextInt(userAdvertisementRepository.getApprovedCount() + 1);

        if (demographicDataList.isEmpty()) {
            toDisplay = userAdvertisementRepository.findAll().get(randInt).getAdvertisement();
        } else {
            Map<String, Integer> categoryCounts = demographicDataList.stream()
                    .filter(d -> d.getAge() > 8)
                    .collect(Collectors.groupingBy(d -> getAgeRange(d.getAge()) + "-" + d.getGender(), Collectors.summingInt(d -> 1)));

            List<String> topCategories = categoryCounts.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(2)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            toDisplay = findAdvertisementByDemographics(topCategories);
            if (toDisplay == null) {
                toDisplay = userAdvertisementRepository.findAll().get(randInt).getAdvertisement();
            }
        }
        return cloudStorageService.fetchAdvertisementFromCloud(toDisplay.getAdvertisementUrl());
    }


    private String getAgeRange(int age) {
        if (age >= 0 && age <= 18) {
            return "08-18";
        } else if (age >= 19 && age <= 60) {
            return "19-60";
        } else if (age >= 61) {
            return "61-100";
        } else {
            return null;
        }
    }

    private Advertisement findAdvertisementByDemographics(List<String> topCategories) {
        Random rand = new Random();
        for (String category : topCategories) {
            String takenRange = category.substring(0, 5);
            String takenGender = category.substring(6);
            for (int checkingPriority = 1; checkingPriority <= 3; checkingPriority++) {
                List<UserAdvertisement> matchingAds = userAdvertisementRepository.findMatchingAdsByDemographicAndPriority(takenRange, takenGender, checkingPriority, Status.ACTIVE_STATUS.status());
                if (!matchingAds.isEmpty()) {
                    return matchingAds.get(rand.nextInt(matchingAds.size())).getAdvertisement();
                }
                matchingAds = userAdvertisementRepository.findMatchingAdsByDemographicAndPriority(takenRange, "Both", checkingPriority, Status.ACTIVE_STATUS.status());
                if (!matchingAds.isEmpty()) {
                    return matchingAds.get(rand.nextInt(matchingAds.size())).getAdvertisement();
                }
            }
        }
        return null;
    }

    @Override
    public String deleteAd(int id, int adId) {
        UserAdvertisement userAdvertisement = userAdvertisementRepository.findById(adId).orElse(null);
        if (userAdvertisement != null) {
            Advertisement advertisement = userAdvertisement.getAdvertisement();
            String fileName =  userAdvertisement.getAdvertisement().getAdvertisementUrl();
            try{
                userAdvertisementRepository.deleteByUniqueIdentifier(userAdvertisement.getUniqueIdentifier());
            }catch (Exception e) {
                throw new AdsyncException(AdsyncApplicationError.ADVERTISEMENT_DELETE_ERROR);
            }
            advertisementRepository.delete(advertisement);
            cloudStorageService.deleteFile(fileName);
            return "Advertisement deleted successfully";
        } else {
            return "Advertisement not found";
        }
    }

    @Override
    public UserAdvertisement getUserAdvertisementById(int id) {
        return userAdvertisementRepository.getAllById(id);
    }

    @Override
    public List<UserAdvertisement> getUserWisePendingAdvertisement(User user) {
        return userAdvertisementRepository.getUserWisePendingAdvertisement(user);
    }

    @Override
    public List<UserAdvertisement> getAllPendingAdvertisement() {
        return userAdvertisementRepository.findAllPendingAds();
    }

    @Override
    public List<UserAdvertisement> getAdByUniqueIdentifier(String uniquieIdentifier) {
        return userAdvertisementRepository.getAdsListByUniqueIdentifier(uniquieIdentifier);
    }

    @Override
    public void updateAdStatus(String status, List<UserAdvertisement> userAdvertisements) {
        userAdvertisements.forEach(userAdvertisement -> {
            userAdvertisement.setStatus(status);
            userAdvertisementRepository.save(userAdvertisement);
        });
    }


}