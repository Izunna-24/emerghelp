package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.models.EmergencyRequest;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.dtos.requests.*;
import com.emerghelp.emerghelp.dtos.responses.RegisterUserResponse;
import com.emerghelp.emerghelp.dtos.responses.UpdateProfileResponse;
import com.emerghelp.emerghelp.dtos.responses.ViewProfileResponse;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface UserService {
RegisterUserResponse register(RegisterUserRequest registerUserRequest);
User getById(Long id);
User getUserByUsername(String username);
ViewProfileResponse viewProfile(Long id);
UpdateProfileResponse updateProfile(Long id, JsonPatch jsonPatch);
User requestMedic(RequestMedicRequest requestMedicRequest);

List<EmergencyRequest> viewAllRequests(Long id);
User getRequestById(Long id);
User rateMedic();
User cancelRequest();
}
