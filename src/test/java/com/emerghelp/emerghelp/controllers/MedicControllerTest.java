package com.emerghelp.emerghelp.controllers;


import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.models.OrderMedic;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.MedicRepository;
import com.emerghelp.emerghelp.data.repositories.OrderMedicRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.AcceptOrderMedicDTO;
import com.emerghelp.emerghelp.exceptions.MedicNotFoundException;
import com.emerghelp.emerghelp.exceptions.OrderMedicNotFoundException;
import com.emerghelp.emerghelp.exceptions.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.emerghelp.emerghelp.data.constants.OrderMedicStatus.PENDING;
import static org.assertj.core.api.Fail.fail;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/data.sql"})
public class MedicControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MedicRepository medicRepository;
    @Autowired
    private OrderMedicRepository orderMedicRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(authorities = {"USER"})
    public void testOrderMedic() {
        try {
            User user = userRepository.findById(200L).orElseThrow(() -> new UserNotFoundException("User not found"));
            Medic medic = medicRepository.findById(300L).orElseThrow(() -> new MedicNotFoundException("Medic not found"));

            mockMvc.perform(multipart("/api/v1/medics/order")
                            .param("userId", String.valueOf(user.getId()))
                            .param("description", "Heart attack")
                            .param("longitude", String.valueOf(medic.getLongitude()))
                            .param("latitude", String.valueOf(medic.getLatitude()))
                            .param("medicId", String.valueOf(medic.getId()))
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(status().isCreated())
                    .andDo(print());
        } catch (Exception exception) {
            fail("Exception occurred during test: " + exception.getMessage());
        }

    }
        @Test
        @WithMockUser(authorities = {"MEDIC"})
        public void testAcceptOrder() {
                    try {
                        User user = new User();
                        user.setId(200L);
                        user.setLatitude(3.377417);
                        user.setLongitude(6.506125);
                        userRepository.save(user);

                        Medic medic = new Medic();
                        medic.setId(300L);
                        medic.setLongitude(6.506125);
                        medic.setLatitude(3.377417);
                        medic.setLicenseNumber("2238");
                        medicRepository.save(medic);

                        OrderMedic orderMedic = new OrderMedic();
                        orderMedic.setId(1L);
                        orderMedic.setUser(user);
                        orderMedic.setDescription("headedache");
                        orderMedic.setOrderMedicStatus(PENDING);
                        orderMedic.setLongitude(medic.getLongitude());
                        orderMedic.setLatitude(medic.getLatitude());
                        orderMedicRepository.save(orderMedic);



                        mockMvc.perform(multipart("/api/v1/medics/accept-order")
                                        .param("orderId", String.valueOf(orderMedic.getId()))
                                        .param("medicId", String.valueOf(medic.getId()))
                                        .param("userId", String.valueOf(user.getId()))
                                        .contentType(MediaType.MULTIPART_FORM_DATA))
                                .andExpect(status().isCreated())
                                .andDo(print());
                    } catch (Exception exception) {
                        fail("Exception occurred during test: " + exception.getMessage());
                    }
                }
            }





