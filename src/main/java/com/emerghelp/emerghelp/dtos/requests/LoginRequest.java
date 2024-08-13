package com.emerghelp.emerghelp.dtos.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

        @JsonProperty("email")
        @Email(regexp="^(?=[a-zA-Z])[a-zA-Z]+([0-9])([_+!`])+@(?=[a-zA-Z])([a-zA-Z]+)([0-9])([a-zA-Z0-9._!~+-])+\\.[a-zA-Z]{2,}$")
        private String email;
        @Pattern(regexp = "[a-zA-Z0-9]{8,}+[+-.,/]",message = "password must include alphabets, symbols and numbers")
        private String password;

}
