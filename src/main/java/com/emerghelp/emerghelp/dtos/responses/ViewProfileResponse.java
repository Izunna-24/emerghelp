package com.emerghelp.emerghelp.dtos.responses;

import com.emerghelp.emerghelp.data.constants.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewProfileResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
//    private String profileUrl;
    private String email;
    private Gender gender;
}
