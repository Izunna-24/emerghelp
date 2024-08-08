package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.dtos.requests.MedicRequestDTO;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.responses.MedicRequestResponse;
import com.emerghelp.emerghelp.dtos.responses.OrderMedicHistory;
import com.emerghelp.emerghelp.dtos.responses.RegisterUserResponse;

import java.util.List;

public interface UserService {
RegisterUserResponse register(RegisterUserRequest registerUserRequest);
User getById(Long id);
User getUserByEmail(String username);
}
