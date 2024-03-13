package com.project.adsync.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.adsync.controller.UserController;
import com.project.adsync.domain.ReportedIssue;
import com.project.adsync.domain.User;
import com.project.adsync.enums.AdsyncApplicationError;
import com.project.adsync.enums.Status;
import com.project.adsync.exception.AdsyncException;
import com.project.adsync.model.AdsyncResponse;
import com.project.adsync.model.request.LoginReq;
import com.project.adsync.model.request.UserRegReq;
import com.project.adsync.model.response.CustomerLoginResponse;
import com.project.adsync.service.UserService;
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

import java.util.HashMap;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegisterUser_Success() throws Exception {
        UserRegReq userRegReq = new UserRegReq(); // Provide a valid UserRegReq object
        User newUser = new User(); // Provide a valid User object

        when(userService.registerUser(userRegReq)).thenReturn(newUser);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/adSync.api/user/register")
                        .content(asJsonString(userRegReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"));
        // Add additional assertions based on your actual implementation
    }

    @Test
    void testRegisterUser_Error() throws Exception {
        UserRegReq userRegReq = new UserRegReq(); // Provide a valid UserRegReq object

        when(userService.registerUser(userRegReq)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/adSync.api/user/register")
                        .content(asJsonString(userRegReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("404"));
        // Add additional assertions based on your actual implementation
    }

    @Test
    void testLogin_Success() throws Exception {
        LoginReq loginReq = new LoginReq(); // Provide a valid LoginReq object
        User user = new User(); // Provide a valid User object
        CustomerLoginResponse customerLoginResponse = new CustomerLoginResponse(); // Provide a valid CustomerLoginResponse object

        when(userService.loginUser(loginReq)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/adSync.api/user/login")
                        .content(asJsonString(loginReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"));
        // Add additional assertions based on your actual implementation
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        LoginReq loginReq = new LoginReq(); // Provide an invalid LoginReq object

        when(userService.loginUser(loginReq)).thenThrow(new AdsyncException(AdsyncApplicationError.INVALID_CREDENTIALS));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/adSync.api/user/login")
                        .content(asJsonString(loginReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("400"));
        // Add additional assertions based on your actual implementation
    }

    @Test
    void testGetUserDashboardDetails_Success() throws Exception {
        int userId = 1; // Provide a valid user ID
        HashMap<String, Integer> dashboardDetails = new HashMap<>(); // Provide valid dashboard details

        when(userService.getUserDashboardDetails(userId)).thenReturn(dashboardDetails);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/user/{userId}/getUserDashboardDetails", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"));
        // Add additional assertions based on your actual implementation
    }

    @Test
    void testReportIssue_Success() throws Exception {
        int userId = 1; // Provide a valid user ID
        ReportedIssue reportedIssue = new ReportedIssue(); // Provide a valid ReportedIssue object

        when(userService.reportIssue(userId, reportedIssue)).thenReturn("Issue reported successfully");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/adSync.api/user/{id}/reportIssue", userId)
                        .content(asJsonString(reportedIssue))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"));
        // Add additional assertions based on your actual implementation
    }

    @Test
    void testReportIssue_UserNotFound() throws Exception {
        int userId = 1; // Provide an invalid user ID
        ReportedIssue reportedIssue = new ReportedIssue(); // Provide a valid ReportedIssue object

        when(userService.reportIssue(userId, reportedIssue)).thenThrow(new AdsyncException(AdsyncApplicationError.USER_NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/adSync.api/user/{id}/reportIssue", userId)
                        .content(asJsonString(reportedIssue))
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

