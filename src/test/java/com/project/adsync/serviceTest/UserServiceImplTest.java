package com.project.adsync.serviceTest;

import com.project.adsync.domain.ReportedIssue;
import com.project.adsync.domain.User;
import com.project.adsync.domain.UserRole;
import com.project.adsync.enums.AdsyncApplicationError;
import com.project.adsync.enums.Status;
import com.project.adsync.exception.AdsyncException;
import com.project.adsync.model.request.LoginReq;
import com.project.adsync.model.request.UserRegReq;
import com.project.adsync.repository.*;
import com.project.adsync.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BusinessCategoryRepository businessCategoryRepository;

    @Mock
    private UserAdvertisementRepository userAdvertisementRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private ReportedIssueRepository reportedIssueRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegisterUser_Success() {
        UserRegReq userRegReq = new UserRegReq(); // Create a UserRegReq object with required fields
        User mockedUser = new User(); // Create a mocked user object
        when(userRepository.findUserByEmail(userRegReq.getEmail())).thenReturn(Optional.empty());
        when(userRoleRepository.getUserById(2)).thenReturn(new UserRole()); // Assuming getUserById(2) returns a valid role

        User result = userService.registerUser(userRegReq);

        assertNotNull(result);
        assertEquals(mockedUser, result);
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        UserRegReq userRegReq = new UserRegReq(); // Create a UserRegReq object with required fields
        when(userRepository.findUserByEmail(userRegReq.getEmail())).thenReturn(Optional.of(new User()));

        User result = userService.registerUser(userRegReq);

        assertNull(result);
    }

    @Test
    void testLogin_Success() {
        LoginReq loginReq = new LoginReq(); // Create a LoginReq object with required fields
        User mockedUser = new User(); // Create a mocked user object
        when(userRepository.findByEmail(loginReq.getEmail())).thenReturn(mockedUser);

        User result = userService.loginUser(loginReq);

        assertEquals(mockedUser, result);
    }

    @Test
    void testLogin_InvalidCredentials() {
        LoginReq loginReq = new LoginReq(); // Create a LoginReq object with required fields
        when(userRepository.findByEmail(loginReq.getEmail())).thenReturn(null);

        AdsyncException exception = assertThrows(AdsyncException.class, () -> {
            userService.loginUser(loginReq);
        });

        assertEquals(AdsyncApplicationError.INVALID_CREDENTIALS, exception.getMessage());
    }

     @Test
     void testGetUserDashboardDetails_Success() {
         int userId = 1; // Provide a valid user ID
         User mockedUser = new User(); // Create a mocked user object
         when(userRepository.getReferenceById(userId)).thenReturn(mockedUser);
         // Mock other dependencies as needed

         HashMap<String, Integer> dashboardDetails = userService.getUserDashboardDetails(userId);

         assertNotNull(dashboardDetails);
         // Add assertions based on expected behavior
     }

    @Test
    void testGetUserByUserName_Success() {
        String userName = "test@example.com"; // Provide a valid username
        User mockedUser = new User(); // Create a mocked user object
        when(userRepository.findByEmail(userName)).thenReturn(mockedUser);

        User result = userService.getUserByUserName(userName);

        assertEquals(mockedUser, result);
    }

    @Test
    void testGetPendingUsers_Success() {
        List<User> mockedUsers = new ArrayList<>(); // Create a list of mocked users
        when(userRepository.findAll()).thenReturn(mockedUsers);

        List<User> result = userService.getPendingUsers();

        assertEquals(mockedUsers, result);
    }

/*    @Test
    void testReportIssue_Success() {
        int userId = 1; // Provide a valid user ID
        ReportedIssue issue = new ReportedIssue(); // Create a reported issue object
        User mockedUser = new User(); // Create a mocked user object
        when(userRepository.getReferenceById(userId)).thenReturn(mockedUser);

        String result = userService.reportIssue(userId, issue);

        assertEquals("Issue reported successfully", result);
    }*/

  /*  @Test
    void testReportIssue_UserNotFound() {
        int userId = 1; // Provide an invalid user ID
        ReportedIssue issue = new ReportedIssue(); // Create a reported issue object
        when(userRepository.getReferenceById(userId)).thenReturn(null);

        AdsyncException exception = assertThrows(AdsyncException.class, () -> {
            userService.reportIssue(userId, issue);
        });

        assertEquals(AdsyncApplicationError.USER_NOT_FOUND, exception.getMessage());
    }
*/
    @Test
    void testGetUserWiseIssues_Success() {
        User mockedUser = new User(); // Create a mocked user object
        List<ReportedIssue> mockedIssues = new ArrayList<>(); // Create a list of mocked reported issues
        when(reportedIssueRepository.getUserWiseIssues(mockedUser)).thenReturn(mockedIssues);

        List<ReportedIssue> result = userService.getUserWiseIssues(mockedUser);

        assertEquals(mockedIssues, result);
    }

    @Test
    void testGetAllPendingReportedIssues_Success() {
        List<ReportedIssue> mockedIssues = new ArrayList<>(); // Create a list of mocked reported issues
        when(reportedIssueRepository.findAll()).thenReturn(mockedIssues);

        List<ReportedIssue> result = userService.getAllPendingReportedIssues();

        assertEquals(mockedIssues, result);
    }
}
