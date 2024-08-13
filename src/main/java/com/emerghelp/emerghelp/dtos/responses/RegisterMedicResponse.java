package com.emerghelp.emerghelp.dtos.responses;

import com.emerghelp.emerghelp.data.constants.Gender;
import lombok.*;

@Getter
@Setter
public class RegisterMedicResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private String photoUrl;
    private String specialization;
    private String licenseNumber;
    private String message;
    private Boolean isAvailable;

}
