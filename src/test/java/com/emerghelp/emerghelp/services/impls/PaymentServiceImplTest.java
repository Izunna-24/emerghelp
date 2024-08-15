package com.emerghelp.emerghelp.services.impls;


import com.emerghelp.emerghelp.dtos.requests.PaymentRequest;
import com.emerghelp.emerghelp.dtos.responses.PaymentResponse;
import com.emerghelp.emerghelp.dtos.responses.VerifyPaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    private PaymentServiceImpl paymentService;


    @Test
    void initializePayment() throws JsonProcessingException {
        PaymentRequest paymentRequest = PaymentRequest.builder().email("goat@email.com").amount(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(100))).build();
        PaymentResponse paymentResponse = paymentService.initializePayment(paymentRequest);
        System.out.println(new ObjectMapper().writeValueAsString(paymentResponse));
        assertThat(paymentResponse.getData()).isNotNull();
    }

  @Test
    void verifyPayment() throws JsonProcessingException{
      VerifyPaymentResponse verifyPaymentResponse = paymentService.verifyPayment("450vm5r3vy");
      System.out.println(new ObjectMapper().writeValueAsString(verifyPaymentResponse));
      assertThat(verifyPaymentResponse.getMessage()).isEqualTo("Verification successful");
  }


}