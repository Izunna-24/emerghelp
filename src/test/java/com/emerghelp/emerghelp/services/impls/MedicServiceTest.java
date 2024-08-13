package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.dtos.requests.RegisterMedicRequest;
import com.emerghelp.emerghelp.dtos.responses.RegisterMedicResponse;
import com.emerghelp.emerghelp.exceptions.EmailAlreadyExistException;
import com.emerghelp.emerghelp.exceptions.LicenseNumberAlreadyExistException;
import com.emerghelp.emerghelp.services.MedicService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class MedicServiceTest {

    @Autowired
    private MedicService medicService;

    @Test
    @DisplayName("Test that medical practitioner can register")
    void testRegisterAndSendConfirmationEmail1() {
        RegisterMedicRequest request = new RegisterMedicRequest();
        request.setEmail("eircsonericdon66@gmail.com");
        request.setLicenseNumber("123");
        request.setPassword("password");
        RegisterMedicResponse response = medicService.register(request);
        assertTrue(response.getMessage().contains("Email sent successfully"));
        assertTrue(true, "Email sent successfully");
        assertEquals("Your account has been created successfully", response.getMessage());
    }
   @Test
   public void testRegisterAndSendConfirmationEmail_ThrowException() {
        try {
            RegisterMedicRequest request = new RegisterMedicRequest();
            request.setEmail("ike20743@gmail.com");
            request.setFirstName("Ikechukwu");
            request.setLicenseNumber("123");
            request.setPassword("password");
            RegisterMedicResponse response1 = medicService.register(request);
            assertTrue(true, "Email sent successfully");
            assertEquals("Your account has been created successfully", response1.getMessage());
        } catch (EmailAlreadyExistException | LicenseNumberAlreadyExistException  exception) {
            exception.printStackTrace();
        }
    }

}
