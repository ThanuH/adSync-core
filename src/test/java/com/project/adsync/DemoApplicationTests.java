package com.project.adsync;

import com.project.adsync.controller.AdminController;
import com.project.adsync.controller.AdvertisementController;
import com.project.adsync.controller.UserController;
import com.project.adsync.service.AdminService;
import com.project.adsync.service.AdvertisementService;
import com.project.adsync.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserController userController;

	@Autowired
	private AdvertisementController advertisementController;

	@Autowired
	private AdminController adminController;

	@Autowired
	private UserService userService;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private AdminService adminService;

	@Test
	public void contextLoads() {
		// Check if the application context loads successfully
		assertThat(restTemplate).isNotNull();
		assertThat(userController).isNotNull();
		assertThat(advertisementController).isNotNull();
		assertThat(adminController).isNotNull();

		assertThat(userService).isNotNull();
		assertThat(advertisementService).isNotNull();
		assertThat(adminService).isNotNull();
	}

	@Test
	public void testHealthEndpoint() {
		// Test the health endpoint
		String url = "http://localhost:" + port + "/actuator/health";
		String response = this.restTemplate.getForObject(url, String.class);
		assertThat(response).contains("UP");
	}

}
