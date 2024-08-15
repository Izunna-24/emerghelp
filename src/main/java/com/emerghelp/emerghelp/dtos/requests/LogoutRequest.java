package com.emerghelp.emerghelp.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogoutRequest {
    private String message;
    private boolean isLoggedIn;
    private String email;
}
