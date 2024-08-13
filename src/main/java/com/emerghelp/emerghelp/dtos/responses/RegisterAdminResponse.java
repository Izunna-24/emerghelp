package com.emerghelp.emerghelp.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterAdminResponse {
    @JsonProperty("admin_id")
    private Long adminId;
    @JsonProperty("userName")
    private String userName;
    private String email;
    private String message;
}
