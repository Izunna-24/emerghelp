package com.emerghelp.emerghelp.dtos.requests;

import com.emerghelp.emerghelp.data.constants.Gender;
import com.emerghelp.emerghelp.data.constants.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RegisterUserRequest {
private String firstName;
private String lastName;
@JsonProperty("email")
@Email(regexp="^(?=[a-zA-Z])[a-zA-Z]+([0-9]*)([_+!`]*)+@(?=[a-zA-Z])([a-zA-Z]+)([0-9]*)([a-zA-Z0-9._!~+-]*)+\\.[a-zA-Z]{2,}$")
private String email;
@Pattern(regexp = "[a-zA-Z0-9]{8,}+[+-.,/]",message = "password must include alphabets, symbols and numbers")
private String password;
@NotNull
@Pattern(regexp = "^?\\+[0-9]{11,14}$",message = "invalid phone number")
private String phoneNumber;
private Gender gender;
}
