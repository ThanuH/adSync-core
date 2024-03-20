package com.project.adsync.controllerTest;

import com.project.adsync.controller.AdminController;
import com.project.adsync.domain.ReportedIssue;
import com.project.adsync.domain.User;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    void shouldReturnDashboardDetailsWhenGetTotalNoOfUsers() throws Exception {
        HashMap<String, Integer> dashboardDetails = new HashMap<>();
        dashboardDetails.put("totalUsers", 100);
        dashboardDetails.put("activeUsers", 80);

        when(adminService.getDashboardDetails()).thenReturn(dashboardDetails);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/admin/getDashboardDetails")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseObject.totalUsers").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseObject.activeUsers").value(80));
    }

    @Test
    void shouldReturnUserWhenGetPendingUsersWithUserName() throws Exception {
        String userName = "testUser";
        User user = new User();
        user.setEmail(userName);

        when(userService.getUserByUserName(userName)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/admin/getAllUsers")
                        .param("userName", userName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseObject.userName").value(userName));
    }

    @Test
    void shouldReturnUserNotFoundWhenGetPendingUsersWithInvalidUserName() throws Exception {
        String userName = "invalidUser";

        when(userService.getUserByUserName(userName)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/admin/getAllUsers")
                        .param("userName", userName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseObject").value("User not found"));
    }

    @Test
    void shouldReturnPendingUsersWhenGetPendingUsersWithoutUserName() throws Exception {
        User user1 = new User();
        user1.setEmail("pendingUser1");
        User user2 = new User();
        user2.setEmail("pendingUser2");
        List<User> pendingUsers = Arrays.asList(user1, user2);

        when(userService.getPendingUsers()).thenReturn(pendingUsers);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/admin/getAllUsers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseObject[0].userName").value("pendingUser1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseObject[1].userName").value("pendingUser2"));
    }

    @Test
    void shouldReturnUserWiseIssuesWhenGetAllPendingReportedIssuesWithUserName() throws Exception {
        String userName = "testUser";
        User user = new User();
        user.setEmail(userName);
        ReportedIssue issue1 = new ReportedIssue();
        issue1.setId(1);
        ReportedIssue issue2 = new ReportedIssue();
        issue2.setId(2);
        List<ReportedIssue> userWiseIssues = Arrays.asList(issue1, issue2);

        when(userService.getUserByUserName(userName)).thenReturn(user);
        when(userService.getUserWiseIssues(user)).thenReturn(userWiseIssues);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/admin/getAllPendingReportedIssues")
                        .param("userName", userName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseObject[0].issueId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseObject[1].issueId").value(2));
    }

    @Test
    void shouldReturnAllPendingReportedIssuesWhenGetAllPendingReportedIssuesWithoutUserName() throws Exception {
        ReportedIssue issue1 = new ReportedIssue();
        issue1.setId(1);
        ReportedIssue issue2 = new ReportedIssue();
        issue2.setId(2);
        List<ReportedIssue> reportedIssues = Arrays.asList(issue1, issue2);

        when(userService.getAllPendingReportedIssues()).thenReturn(reportedIssues);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adSync.api/admin/getAllPendingReportedIssues")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseObject[0].issueId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseObject[1].issueId").value(2));
    }
}