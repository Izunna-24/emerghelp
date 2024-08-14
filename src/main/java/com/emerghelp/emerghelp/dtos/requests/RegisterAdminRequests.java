package com.emerghelp.emerghelp.dtos.requests;

import com.emerghelp.emerghelp.data.constants.Gender;
import com.emerghelp.emerghelp.data.constants.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterAdminRequests {
    private String userName;
    private String password;
    private String email;
    private Gender gender;
    private Role role;
}
