package com.emerghelp.emerghelp.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptOrderResponse {
    private Long medicalPractitionerId;
    private String name;
    private String phoneNumber;
    private String message;
}
