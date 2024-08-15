package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.dtos.requests.PaymentRequest;
import com.emerghelp.emerghelp.dtos.responses.PaymentResponse;
import com.emerghelp.emerghelp.dtos.responses.VerifyPaymentResponse;

public interface PaymentService {
    PaymentResponse initializePayment(PaymentRequest paymentRequest);
    VerifyPaymentResponse verifyPayment(String reference);


}