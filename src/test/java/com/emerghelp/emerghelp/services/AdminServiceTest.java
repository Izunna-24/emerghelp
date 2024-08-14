package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.constants.Gender;
import com.emerghelp.emerghelp.data.constants.Role;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.AdminRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.ActivateUserRequests;
import com.emerghelp.emerghelp.dtos.requests.DeactivateUserRequests;
import com.emerghelp.emerghelp.dtos.requests.RegisterAdminRequests;
import com.emerghelp.emerghelp.dtos.responses.RegisterAdminResponse;
import com.emerghelp.emerghelp.exceptions.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Test that admin can created")
    public void createAdminTest() {
        RegisterAdminRequests requests = new RegisterAdminRequests();
        requests.setEmail("satana@gmail.com");
        requests.setPassword("7788");
        requests.setGender(Gender.MALE);
        requests.setRole(Role.ADMIN);
        RegisterAdminResponse response = adminService.registerAdmin(requests);
        assertNotNull(response);
        assertEquals("Your account as an admin has been created successfully", response.getMessage());
    }
    @Test
    @DisplayName("Test that admin deactivate user")
    public void deactivateUserTest() {
        User user = new User();
        user.setEmail("ridrijulmi@gufum.com");
        user.setPassword("password");
        user.setId(200L);
        user.setEnabled(false);
        DeactivateUserRequests deactivateUserRequests = new DeactivateUserRequests();
        deactivateUserRequests.setUserId(user.getId());
        adminService.deactivateUserAccount(deactivateUserRequests);
        User updatedUser = userRepository.findById(user.getId())
                .orElseThrow(()-> new UserNotFoundException("User not not found"));
        assertFalse(updatedUser.isEnabled(), "User account was successfully deactivated");
    }
    @Test
    @DisplayName("Test that admin can activate user")
    public void activateUserTest() {
        User user = new User();
        user.setEmail("ridrijulmi@gufum.com");
        user.setPassword("password");
        user.setId(200L);
        user.setEnabled(true);
        DeactivateUserRequests deactivateUserRequests = new DeactivateUserRequests();
        deactivateUserRequests.setUserId(user.getId());
        adminService.deactivateUserAccount(deactivateUserRequests);

        User updatedUser = userRepository.findById(user.getId())
                .orElseThrow(()-> new UserNotFoundException("User not not found"));
        assertFalse(updatedUser.isEnabled(), "User account was successfully activated");

        ActivateUserRequests activateUserRequests = new ActivateUserRequests();
        activateUserRequests.setUserId(user.getId());
        adminService.activateUserAccount(activateUserRequests);
        updatedUser = userRepository.findById(user.getId())
                .orElseThrow(()-> new UserNotFoundException("User not not found"));
        assertTrue(updatedUser.isEnabled(), "User account was successfully activated");

    }
}