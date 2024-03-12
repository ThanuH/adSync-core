package com.project.adsync.serviceTest;

import com.project.adsync.domain.Advertisement;
import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.User;
import com.project.adsync.enums.Status;
import com.project.adsync.model.BasicAdDetails;
import com.project.adsync.model.request.UploadAdReq;
import com.project.adsync.repository.AdvertisemntRepository;
import com.project.adsync.repository.BusinessCategoryRepository;
import com.project.adsync.repository.UserAdvertisementRepository;
import com.project.adsync.repository.UserRepository;
import com.project.adsync.service.AdvertisementServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdvertisementServiceImplTest {

    @Mock
    private BusinessCategoryRepository businessCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserAdvertisementRepository userAdvertisementRepository;

    @Mock
    private AdvertisemntRepository advertisementRepository;

    @InjectMocks
    private AdvertisementServiceImpl advertisementService;

    @Test
    void testUploadUserAdvertisement_Success() {
        UploadAdReq uploadAdReq = new UploadAdReq(); // Provide a valid UploadAdReq object
        User user = new User(); // Provide a valid User object
        Advertisement advertisement = new Advertisement(); // Provide a valid Advertisement object
        BusinessCategory businessCategory = new BusinessCategory(); // Provide a valid BusinessCategory object

        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(advertisement);
        when(businessCategoryRepository.findById(anyInt())).thenReturn(Optional.of(businessCategory));

        String result = advertisementService.uploadUserAdvertisement(uploadAdReq, user);

        assertEquals("Advertisement uploaded successfully", result);
    }

    @Test
    void testUploadUserAdvertisement_Error() {
        UploadAdReq uploadAdReq = new UploadAdReq(); // Provide a valid UploadAdReq object
        User user = new User(); // Provide a valid User object

        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(null);

        String result = advertisementService.uploadUserAdvertisement(uploadAdReq, user);

        assertEquals("Error while uploading", result);
    }

    @Test
    void testUploadUserAdvertisement_Update_Success() {
        UploadAdReq uploadAdReq = new UploadAdReq(); // Provide a valid UploadAdReq object
        uploadAdReq.setUpdate(true);
        User user = new User(); // Provide a valid User object
        Advertisement existingAdvertisement = new Advertisement(); // Provide an existing Advertisement object
        BusinessCategory businessCategory = new BusinessCategory(); // Provide a valid BusinessCategory object

        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(existingAdvertisement);
        when(businessCategoryRepository.findById(anyInt())).thenReturn(Optional.of(businessCategory));
//        when(userAdvertisementRepository.getAdsListByUniqueIdentifier(anyString())).thenReturn(/* Provide a list of UserAdvertisement objects */);

        String result = advertisementService.uploadUserAdvertisement(uploadAdReq, user);

        assertEquals("Advertisement updated successfully", result);
    }

    @Test
    void testUploadUserAdvertisement_Update_UrlUpdated_Success() {
        UploadAdReq uploadAdReq = new UploadAdReq(); // Provide a valid UploadAdReq object
        uploadAdReq.setUpdate(true);
        User user = new User(); // Provide a valid User object
        Advertisement existingAdvertisement = new Advertisement(); // Provide an existing Advertisement object with a different URL
        BusinessCategory businessCategory = new BusinessCategory(); // Provide a valid BusinessCategory object

        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(existingAdvertisement);
        when(businessCategoryRepository.findById(anyInt())).thenReturn(Optional.of(businessCategory));
//        when(userAdvertisementRepository.getAdsListByUniqueIdentifier(anyString())).thenReturn(/* Provide a list of UserAdvertisement objects */);

        String result = advertisementService.uploadUserAdvertisement(uploadAdReq, user);

        assertEquals("Advertisement updated successfully", result);
    }

    @Test
    void testUploadUserAdvertisement_Update_UrlNotUpdated_Success() {
        UploadAdReq uploadAdReq = new UploadAdReq(); // Provide a valid UploadAdReq object
        uploadAdReq.setUpdate(true);
        User user = new User(); // Provide a valid User object
        Advertisement existingAdvertisement = new Advertisement(); // Provide an existing Advertisement object with the same URL
        BusinessCategory businessCategory = new BusinessCategory(); // Provide a valid BusinessCategory object

        when(businessCategoryRepository.findById(anyInt())).thenReturn(Optional.of(businessCategory));
//        when(userAdvertisementRepository.getAdsListByUniqueIdentifier(anyString())).thenReturn(/* Provide a list of UserAdvertisement objects */);

        String result = advertisementService.uploadUserAdvertisement(uploadAdReq, user);

        assertEquals("Advertisement updated successfully", result);
    }

}
