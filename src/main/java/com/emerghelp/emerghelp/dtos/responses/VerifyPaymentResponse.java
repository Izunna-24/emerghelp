package com.emerghelp.emerghelp.dtos.responses;

import com.emerghelp.emerghelp.dtos.requests.VerifyData;
import lombok.NoArgsConstructor;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VerifyPaymentResponse {
 private  String message;
 private Boolean status;
 private VerifyData data;
}