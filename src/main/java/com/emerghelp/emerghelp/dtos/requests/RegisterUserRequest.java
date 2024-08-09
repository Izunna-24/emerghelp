package com.emerghelp.emerghelp.dtos.requests;

import com.emerghelp.emerghelp.data.constants.Gender;
import com.emerghelp.emerghelp.data.constants.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserRequest {
private String firstName;
private String lastName;
private String email;
private String password;
private String phoneNumber;
private Gender gender;
private String profilePictureUrl;
}
