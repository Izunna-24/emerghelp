package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.responses.MedicRequestResponse;
import com.emerghelp.emerghelp.dtos.responses.RegisterUserResponse;

public interface UserService {
RegisterUserResponse register(RegisterUserRequest registerUserRequest);
User getById(Long id);
User getUserByEmail(String username);
MedicRequestResponse requestMedic(Long email,MedicRequest medicRequest);
}
