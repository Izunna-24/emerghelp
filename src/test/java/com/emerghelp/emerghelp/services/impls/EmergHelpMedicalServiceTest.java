package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.dtos.requests.UpgradeToMedicalPractitionerRequest;
import com.emerghelp.emerghelp.dtos.responses.UpgradeToMedicalPractitionerResponse;
import com.emerghelp.emerghelp.services.MedicalService;
import com.emerghelp.emerghelp.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Slf4j
@Sql(scripts = {"/db/data.sql"})
class EmergHelpMedicalServiceTest {

    @Autowired
    private MedicalService medicalService;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Test that medical practitioner can register")
    void testUpgradeToMedicalPractitioner_Success() {
        String email = "ridrijulmi@gufum.com";
        String photoUrl = "picture";
        String specialization = "doctor";
        String licenseNumber = "402";
        long practitionerId = 100;
        User user = new User();
        user.setEmail(email);
        UpgradeToMedicalPractitionerRequest request = new UpgradeToMedicalPractitionerRequest();
        request.setPractitionerId(practitionerId);
        request.setPhotoUrl(photoUrl);
        request.setSpecialization(specialization);
        request.setLicenseNumber(licenseNumber);
        request.setEmail(email);
        UpgradeToMedicalPractitionerResponse response = medicalService.upgradeToMedicalPractitioner(request);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("upgraded to medical practitioner successfully"));
    }
}
