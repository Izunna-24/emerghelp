package com.emerghelp.emerghelp.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpgradeToMedicalPractitionerResponse {
    private Long practitionerId;
    private String photoUrl;
    private String specialization;
    private String licenseNumber;
    private String message;
}
