package com.emerghelp.emerghelp.dtos.responses;

import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentResponse {
    private Boolean status;
    private String message;
    private Object data;
    private Boolean payStatus;
}