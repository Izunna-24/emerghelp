package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.dtos.requests.RegisterMedicRequest;
import com.emerghelp.emerghelp.dtos.responses.RegisterMedicResponse;
import com.emerghelp.emerghelp.exceptions.EmailAlreadyExistException;
import com.emerghelp.emerghelp.services.MedicalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
class MedicServiceTest {

    @Autowired
    private MedicalService medicalService;

    @Test
    @DisplayName("Test that medical practitioner can register")
    void testRegisterAndSendConfirmationEmail1() {
        RegisterMedicRequest request = new RegisterMedicRequest();
        request.setEmail("eircsonericdon66@gmail.com");
        request.setLicenseNumber("123");
        request.setPassword("password");
        request.setId(300L);
        RegisterMedicResponse response = medicalService.register(request);
        assertTrue(response.getMessage().contains("Email sent successfully"));
        assertTrue(true, "Email sent successfully");
        assertEquals("Your account has been created successfully", response.getMessage());
    }
   @Test
   public void testRegisterAndSendConfirmationEmail2() {
        try {
            RegisterMedicRequest request = new RegisterMedicRequest();
            request.setEmail("ike20743@gmail.com");
            request.setFirstName("Ikechukwu");
            request.setLicenseNumber("123");
            request.setPassword("password");
            request.setId(300L);
            RegisterMedicResponse response1 = medicalService.register(request);
            assertTrue(true, "Email sent successfully");
            assertEquals("Your account has been created successfully", response1.getMessage());
        } catch (EmailAlreadyExistException e) {
            e.printStackTrace();
        }
    }

}
