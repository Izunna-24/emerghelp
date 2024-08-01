package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.responses.RegisterUserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.emerghelp.emerghelp.data.constants.Gender.UNDEFINED;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("test that user can be registered on the system")
    public void registerTest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("Jumoke");
        request.setLastName("Joseph");
        request.setEmail("ridrijulmi2@gufum.com");
        request.setPassword("password");
        request.setGender(UNDEFINED);
        request.setPhoneNumber("09078480034");
        RegisterUserResponse response = userService.register(request);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("success"));

    }
}
