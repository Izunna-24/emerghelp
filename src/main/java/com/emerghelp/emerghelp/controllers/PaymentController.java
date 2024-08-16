package com.emerghelp.emerghelp.controllers;

;
import com.emerghelp.emerghelp.dtos.requests.PaymentRequest;
import com.emerghelp.emerghelp.dtos.responses.PaymentResponse;
import com.emerghelp.emerghelp.dtos.responses.VerifyPaymentResponse;
import com.emerghelp.emerghelp.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;

//    @PostMapping("/initialize")
//    public ResponseEntity<PaymentResponse> initializePayment(@Valid @RequestBody PaymentRequest paymentRequest) {
//        PaymentResponse paymentResponse = paymentService.initializePayment(paymentRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(paymentResponse);
//    }
//    @GetMapping("/verify/{reference}")
//    public ResponseEntity<VerifyPaymentResponse> verifyPayment(@PathVariable String reference) {
//        VerifyPaymentResponse verifyPaymentResponse = paymentService.verifyPayment(reference);
//        if (verifyPaymentResponse != null) {
//            return ResponseEntity.ok(verifyPaymentResponse);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
}