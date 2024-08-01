package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.dtos.requests.LoginRequest;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.requests.RequestMedicRequest;
import com.emerghelp.emerghelp.dtos.requests.UpdateProfileRequest;
import com.emerghelp.emerghelp.dtos.responses.RegisterUserResponse;

public interface UserService {
RegisterUserResponse register(RegisterUserRequest registerUserRequest);
User getById(Long id);
User getUserByUsername(String username);
User viewProfile(Long id);
User updateProfile(UpdateProfileRequest updateProfileRequest);
User requestMedic(RequestMedicRequest requestMedicRequest);

User viewAllRequests();
User getRequestById(Long id);
User rateMedic();
User sendMessage();

User cancelRequest();
}
