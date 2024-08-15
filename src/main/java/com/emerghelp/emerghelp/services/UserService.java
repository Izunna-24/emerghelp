package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.dtos.requests.LoginRequest;
import com.emerghelp.emerghelp.dtos.requests.LogoutRequest;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.responses.*;
import com.github.fge.jsonpatch.JsonPatch;

public interface UserService {
RegisterUserResponse register(RegisterUserRequest registerUserRequest);
User getById(Long id);

Boolean verifyToken(String token);
User getUserByEmail(String username);

ViewProfileResponse viewProfile(Long id);
UpdateProfileResponse updateProfile(Long id, JsonPatch jsonPatch);
LoginResponse login(LoginRequest request);
LogoutResponse logout(LogoutRequest request);


}
