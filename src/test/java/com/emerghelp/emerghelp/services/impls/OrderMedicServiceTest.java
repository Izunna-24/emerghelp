package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.constants.OrderMedicStatus;
import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.OrderMedicRepository;
import com.emerghelp.emerghelp.data.repositories.MedicRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.AcceptOrderMedicDTO;
import com.emerghelp.emerghelp.dtos.requests.OrderMedicDTO;
import com.emerghelp.emerghelp.dtos.responses.AcceptOrderMedicResponse;
import com.emerghelp.emerghelp.dtos.responses.OrderMedicResponse;
import com.emerghelp.emerghelp.dtos.responses.OrderMedicHistory;
import com.emerghelp.emerghelp.exceptions.UserNotFoundException;
import com.emerghelp.emerghelp.services.MedicOrderService;
import com.emerghelp.emerghelp.services.MedicService;
import com.emerghelp.emerghelp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback
@Sql(scripts = {"/db/data.sql"})
public class OrderMedicServiceTest {
    @Autowired
    private MedicOrderService medicOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private MedicRepository medicRepository;
    @Autowired
    private OrderMedicRepository orderMedicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MedicService medicService;


    @BeforeEach
    public void setUp() {
        orderMedicRepository.deleteAll();
    }


    @Test
    public void testThatUserCan_orderMedic(){
        User user = getUser();
        Medic medic1 = createMedic(6.506125,  3.377417, "23"); // Sabo Yaba
        Medic medic2 = createMedic(6.59651000, 3.34205000, "65"); //Ikeja
        Medic medic3 = createMedic(6.5166646,  3.38499846, "234"); // Unilag
        medicRepository.saveAll(Arrays.asList(medic1, medic2, medic3));
        OrderMedicDTO orderMedicDTO = getMedicRequest(user, medic1, "homicide");
        OrderMedicResponse response = medicOrderService.orderMedic(orderMedicDTO);
        assertNotNull(response);
        assertEquals(3, response.getAvailableMedic().size());
        assertEquals(medic3.getId(), response.getAvailableMedic().get(2).getId());

    }

    private Medic createMedic(double latitude, double longitude, String licenseNumber) {
        Medic medic = new Medic();
        medic.setEmail("abiodun@gmail.com");
        medic.setPassword("password");
        medic.setLicenseNumber(licenseNumber);
        medic.setLatitude(latitude);
        medic.setLongitude(longitude);
        return medic;
    }

    @Test
    void testOrderMedic_whenNoAvailableMedics() {
         User user = getUser();

        Medic medic1 = createMedic(80.0, 80.0,"99"); // location beyond 30km
        Medic medic2 = createMedic(-80.0, -80.0,"90"); // location beyond 30km
        medicRepository.saveAll(Arrays.asList(medic1, medic2));

        OrderMedicDTO orderMedicDTO = getMedicRequest(user, medic1, "suicide attempt");

        OrderMedicResponse response = medicOrderService.orderMedic(orderMedicDTO);
        assertNotNull(response);
        assertTrue(response.getAvailableMedic().isEmpty());
    }

    private User getUser() {
        User user = new User();
        user.setEmail("otilo@yahoomail.com");
        user.setPassword("password");
        user.setId(200L);
        user = userRepository.save(user);
        return user;
    }

    private static OrderMedicDTO getMedicRequest(User user, Medic medic1, String suicide_attempt) {
        OrderMedicDTO orderMedicDTO = new OrderMedicDTO();
        orderMedicDTO.setUserId(user.getId());
        orderMedicDTO.setLatitude(6.506125);
        orderMedicDTO.setLongitude(3.377417);
        orderMedicDTO.setMedicId(medic1.getId());
        orderMedicDTO.setDescription(suicide_attempt);
        return orderMedicDTO;
    }

    @Test
    void testOrderMedic_withUserNotFound() {
        OrderMedicDTO orderMedicDTO = new OrderMedicDTO();
        orderMedicDTO.setUserId(999L);
        assertThrows(UserNotFoundException.class, () -> {
            medicOrderService.orderMedic (orderMedicDTO);
        });
    }


        @Test
        public void testViewAllOrderFor_NoMedicRequests() {
            Long userId = 200L;
            List<OrderMedicHistory> orderHistory = medicOrderService.viewAllOrderFor(userId);

            assertThat(orderHistory).isEmpty();
        }

        @Test
        public void testViewAllOrderFor_SingleMedicRequest() {
            User user = getUser();
            Medic medic1 = createMedic(80.0, 80.0,"99");
            OrderMedicDTO orderMedicDTO = getMedicRequest(user, medic1, "homicide");
            medicOrderService.orderMedic(orderMedicDTO);
            List<OrderMedicHistory> orderHistory = medicOrderService.viewAllOrderFor(user.getId());
            assertThat(orderHistory).isNotNull();
            assertThat(orderHistory).hasSize(1);
        }

        @Test
        public void testViewAllOrderFor_multipleMedicRequests() {
            User user = getUser();
            Medic medic1 = createMedic(80.0, 80.0,"99");
            Medic medic2 = createMedic(80.0, 80.0,"99");
            OrderMedicDTO orderMedicDTO = new OrderMedicDTO();
            orderMedicDTO.setMedicId(medic1.getId());
            orderMedicDTO.setUserId(user.getId());
            orderMedicDTO.setDescription("heart attack");
            orderMedicDTO.setLongitude(medic1.getLongitude());
            orderMedicDTO.setLatitude(medic1.getLatitude());
            medicOrderService.orderMedic(orderMedicDTO);

            OrderMedicDTO orderMedicDTO2 = new OrderMedicDTO();
            orderMedicDTO2.setMedicId(medic2.getId());
            orderMedicDTO2.setUserId(user.getId());
            orderMedicDTO2.setDescription("heart attack");
            orderMedicDTO2.setLongitude(medic1.getLongitude());
            orderMedicDTO2.setLatitude(medic1.getLatitude());
            medicOrderService.orderMedic(orderMedicDTO2);

            List<OrderMedicHistory> orderHistory = medicOrderService.viewAllOrderFor(user.getId());
            assertThat(orderHistory).hasSize(2);
        }

    @Transactional
    @Test
    @Rollback
    @Sql(scripts = {"/db/data.sql"})
    public void testMedicAcceptOrder_successful() {
        User user = userService.getById(200L);
        Medic medic = medicService.getMedicById(300L);

        OrderMedicDTO orderMedicDTO = new OrderMedicDTO();
        orderMedicDTO.setMedicId(medic.getId());
        orderMedicDTO.setUserId(user.getId());
        orderMedicDTO.setDescription("heart attack");
        orderMedicDTO.setLongitude(medic.getLongitude());
        orderMedicDTO.setLatitude(medic.getLatitude());

        OrderMedicResponse response = medicOrderService.orderMedic(orderMedicDTO);
        assertThat(response.getId()).isNotNull();
        AcceptOrderMedicDTO acceptOrderMedicDTO = new AcceptOrderMedicDTO();
        acceptOrderMedicDTO.setOrderId(response.getId());
        acceptOrderMedicDTO.setMedicId(medic.getId());
        AcceptOrderMedicResponse acceptOrder = medicOrderService.acceptOrder(acceptOrderMedicDTO);
        assertThat(acceptOrder).isNotNull();
        assertThat(acceptOrder.getOrderId()).isEqualTo(response.getId());
        assertThat(acceptOrder.getMedicId()).isEqualTo(medic.getId());
        assertThat(acceptOrder.getStatus()).isEqualTo(OrderMedicStatus.ACCEPTED);
    }
        }




