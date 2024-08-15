package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.config.PaystackConfig;
import com.emerghelp.emerghelp.data.models.Payment;
import com.emerghelp.emerghelp.data.repositories.PaystackPaymentRepository;
import com.emerghelp.emerghelp.dtos.requests.PaymentRequest;
import com.emerghelp.emerghelp.dtos.responses.PaymentResponse;
import com.emerghelp.emerghelp.dtos.responses.VerifyPaymentResponse;
import com.emerghelp.emerghelp.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private final PaystackPaymentRepository paystackPaymentRepository;
    private final PaystackConfig config;
    private final RestTemplate restTemplate;
    @Value("https://api.paystack.co/transaction/initialize")
    private String initializeEndpoint ;
    @Value("https://api.paystack.co/transaction/verify/")
    private String verifyEndpoint;


    @Override
    public PaymentResponse initializePayment(PaymentRequest paymentRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.setBearerAuth(config.getSecretKey());
        HttpEntity<PaymentRequest> paymentRequestHttpEntity = new HttpEntity<>(paymentRequest,headers);
        ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(initializeEndpoint,paymentRequestHttpEntity, PaymentResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            PaymentResponse paystackResponse = response.getBody();
            Payment transaction = new Payment();
            transaction.setPayStatus(paystackResponse.getPayStatus());
            paystackPaymentRepository.save(transaction);
            return paystackResponse;
        } else {
            throw new RuntimeException("Failed to initialize payment");
        }
    }

    @Override
    public VerifyPaymentResponse verifyPayment(String reference) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.setBearerAuth(config.getSecretKey());
        HttpEntity<String> paymentRequestHttpEntity = new HttpEntity<>(reference,headers);
        ResponseEntity<VerifyPaymentResponse> response = restTemplate.exchange(verifyEndpoint+reference,GET,paymentRequestHttpEntity, VerifyPaymentResponse.class);
        return response.getBody();
    }




}