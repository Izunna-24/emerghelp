package com.emerghelp.emerghelp.dtos.requests;


import com.emerghelp.emerghelp.dtos.responses.Authorization;
import lombok.*;

import java.math.BigInteger;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VerifyData {
    private BigInteger id;
    private String domain;
    private String status;
    private String reference;
    private String amount;
    private String gateway_response;
    private String paid_at;
    private String created_at;
    private String channel;
    private String currency;
    private Authorization authorization;
}