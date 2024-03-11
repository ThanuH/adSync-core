package com.project.adsync.controllerTest;

import com.project.adsync.controller.AdminController;
import com.project.adsync.service.AdminService;
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
class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetTotalNoOfUsers_Success() throws Exception {
        HashMap<String, Integer> dashboardDetails = new HashMap<>(); // Provide valid dashboard details

        when(adminService.getDashboardDetails()).thenReturn(dashboardDetails);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/admin/getDashboardDetails")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"));
        // Add additional assertions based on your actual implementation
    }

    @Test
    void testGetPendingUsers_Success() throws Exception {
        // Provide valid test data

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/admin/getAllUsers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"));
        // Add additional assertions based on your actual implementation
    }

    @Test
    void testGetPendingUsers_UserNotFound() throws Exception {
        // Provide test data where the user is not found

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/admin/getAllUsers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("404"));
        // Add additional assertions based on your actual implementation
    }

    @Test
    void testGetAllPendingReportedIssues_Success() throws Exception {
        // Provide valid test data

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/admin/getAllPendingReportedIssues")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"));
        // Add additional assertions based on your actual implementation
    }

    @Test
    void testGetAllPendingReportedIssues_UserNotFound() throws Exception {
        // Provide test data where the user is not found

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/admin/getAllPendingReportedIssues")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("404"));
        // Add additional assertions based on your actual implementation
    }

}
