package com.project.adsync.serviceTest;

import com.project.adsync.repository.ReportedIssueRepository;
import com.project.adsync.repository.UserAdvertisementRepository;
import com.project.adsync.repository.UserRepository;
import com.project.adsync.repository.UserRoleRepository;
import com.project.adsync.service.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserAdvertisementRepository advertisementRepository;

    @Mock
    private ReportedIssueRepository reportedIssueRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

//    @Test
//    void testGetTotalNoOfUsers_Success() {
//        int userRoleId = 2; // Provide a valid user role ID
//        when(userRoleRepository.getUserById(userRoleId)).thenReturn(/* provide a UserRole object */);
//        when(userRepository.getUserCountByType(/* provide a UserRole object */)).thenReturn(5);
//
//        int result = adminService.getTotalNoOfUsers(userRoleId);
//
//        assertEquals(5, result);
//    }

    @Test
    void testGetDashboardDetails_Success() {
        // Mock data and behavior for other dependencies as needed
        when(advertisementRepository.getSubmittedCount()).thenReturn(10);
        when(advertisementRepository.getApprovedCount()).thenReturn(8);
        when(advertisementRepository.getRejectedCount()).thenReturn(2);
        when(reportedIssueRepository.getPendingCount()).thenReturn(3);

        HashMap<String, Integer> result = adminService.getDashboardDetails();

        assertEquals(10, result.get("totalNoOfSubmittedAds"));
        assertEquals(8, result.get("totalNoOfApprovedAds"));
        assertEquals(2, result.get("totalNoOfRejectedAds"));
        assertEquals(3, result.get("totalNoOfPendingIssues"));
    }
}

