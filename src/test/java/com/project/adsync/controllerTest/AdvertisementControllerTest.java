package com.project.adsync.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.adsync.controller.AdvertisementController;
import com.project.adsync.domain.Advertisement;
import com.project.adsync.domain.BusinessCategory;
import com.project.adsync.domain.User;
import com.project.adsync.enums.AdsyncApplicationError;
import com.project.adsync.enums.Status;
import com.project.adsync.exception.AdsyncException;
import com.project.adsync.model.AdsyncResponse;
import com.project.adsync.model.request.UploadAdReq;
import com.project.adsync.service.AdvertisementService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdvertisementControllerTest {

    @Mock
    private AdvertisementService advertisementService;

    @InjectMocks
    private AdvertisementController advertisementController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllBusinessCategories_Success() throws Exception {
        List<BusinessCategory> businessCategories = List.of(/* Provide valid BusinessCategory objects */);

        when(advertisementService.getBussinessCategories()).thenReturn(businessCategories);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/advertisement/getAllBusinessCatergories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"));
        // Add additional assertions based on your actual implementation
    }

    @Test
    void testUploadUserAdvertisement_Success() throws Exception {
        UploadAdReq uploadAdReq = new UploadAdReq(); // Provide a valid UploadAdReq object
        User user = new User(); // Provide a valid User object

        when(advertisementService.uploadUserAdvertisement(uploadAdReq, user)).thenReturn("Advertisement uploaded successfully");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/adSync.api/advertisement/uploadAd")
                        .content(asJsonString(uploadAdReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"));
        // Add additional assertions based on your actual implementation
    }

    @Test
    void testUploadUserAdvertisement_UserNotFound() throws Exception {
        UploadAdReq uploadAdReq = new UploadAdReq(); // Provide a valid UploadAdReq object
        User user = null; // Provide a null User object

        when(advertisementService.uploadUserAdvertisement(uploadAdReq, user)).thenThrow(new AdsyncException(AdsyncApplicationError.USER_NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/adSync.api/advertisement/uploadAd")
                        .content(asJsonString(uploadAdReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("404"));
        // Add additional assertions based on your actual implementation
    }

    // Helper method to convert objects to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

