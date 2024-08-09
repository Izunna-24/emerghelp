package com.emerghelp.emerghelp.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserResponse {
    private Long id;

    private String email;
    private String username;
    private String message;


}

