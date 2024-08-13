package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.MedicRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.MedicRequestDTO;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.responses.MedicRequestResponse;
import com.emerghelp.emerghelp.dtos.responses.OrderMedicHistory;
import com.emerghelp.emerghelp.exceptions.UserNotFoundException;
import com.emerghelp.emerghelp.services.MedicOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicOrderServiceTest {
    @Autowired
    private MedicOrderService medicOrderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MedicRepository medicRepository;

    @Test
    public void testThatUserCanOrderMedic(){
    User user = new User();
    user.setEmail("email.com");
    user.setPassword("password");
    user.setId(200L);
    user = userRepository.save(user);

    Medic medic1 = createMedic(6.506125,  3.377417, "23"); // Sabo Yaba
    Medic medic2 = createMedic(6.59651000, 3.34205000, "65"); //Ikeja
    Medic medic3 = createMedic(6.5166646,  3.38499846, "234"); // Unilag
    medicRepository.saveAll(Arrays.asList(medic1, medic2, medic3));


    MedicRequestDTO medicRequestDTO = new MedicRequestDTO();
        medicRequestDTO.setUserId(user.getId());
        medicRequestDTO.setLatitude(String.valueOf(6.506125));
        medicRequestDTO.setLongitude(String.valueOf(3.377417));
        medicRequestDTO.setMedicId(medic1.getId());
        medicRequestDTO.setDescription("homicide");

        MedicRequestResponse response = medicOrderService.orderMedic(medicRequestDTO);

        assertNotNull(response);
        assertEquals(1, response.getAvailableMedic().size());
        assertEquals(medic1.getId(), response.getAvailableMedic().get(0).getId());

    }

    private Medic createMedic(double latitude, double longitude, String licenseNumber) {
        Medic medic = new Medic();
        medic.setEmail("Test Medic");
        medic.setPassword("password");
        medic.setLicenseNumber(licenseNumber);
        medic.setId(300L);
        medic.setLatitude(String.valueOf(latitude));
        medic.setLongitude(String.valueOf(longitude));
        return medic;
    }

    @Test
    void testOrderMedic_whenNoAvailableMedics() {
        User user = new User();
        user.setEmail("email.com");
        user.setPassword("password");
        user.setId(200L);
        user = userRepository.save(user);

        Medic medic1 = createMedic(80.0, 80.0,"99"); // location beyond 30km
        Medic medic2 = createMedic(-80.0, -80.0,"90"); // location beyond 30km
        medicRepository.saveAll(Arrays.asList(medic1, medic2));

        MedicRequestDTO medicRequestDTO = new MedicRequestDTO();
        medicRequestDTO.setUserId(user.getId());
        medicRequestDTO.setLatitude(String.valueOf(6.506125));
        medicRequestDTO.setLongitude(String.valueOf(3.377417));
        medicRequestDTO.setMedicId(medic1.getId());
        medicRequestDTO.setDescription("suicide attempt");

        MedicRequestResponse response = medicOrderService.orderMedic(medicRequestDTO);
        assertNotNull(response);
        assertTrue(response.getAvailableMedic().isEmpty());
    }

    @Test
    void testOrderMedic_withUserNotFound() {
        MedicRequestDTO medicRequestDTO = new MedicRequestDTO();
        medicRequestDTO.setUserId(999L);
        assertThrows(UserNotFoundException.class, () -> {
            medicOrderService.orderMedic(medicRequestDTO);
        });
    }





    @Test
    public void getRequestHistoryTest() {
        Long userId = 200L;
        List<OrderMedicHistory> orderHistory = medicOrderService.viewAllOrderFor(200L);
        assertThat(orderHistory).hasSize(1);

    }
}
