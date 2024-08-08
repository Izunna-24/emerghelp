package com.emerghelp.emerghelp.dtos.requests;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpgradeToMedicalPractitionerRequest {
    private Long practitionerId;
    private String email;
    private String photoUrl;
    private String specialization;
    private String licenseNumber;
}
