package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.models.MedicRequest;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.requests.RequestMedicRequest;
import com.emerghelp.emerghelp.dtos.responses.*;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
RegisterUserResponse register(RegisterUserRequest registerUserRequest);
User getById(Long id);
User getUserByUsername(String username);

User saveUser(User user);

Boolean verifyToken(String token);
User getUserByEmail(String username);


    ViewProfileResponse viewProfile(Long id);
    UpdateProfileResponse updateProfile(Long id, JsonPatch jsonPatch);
    User requestMedic(RequestMedicRequest requestMedicRequest);

    List<MedicRequest> viewAllRequests(Long id);
    User getRequestById(Long id);
    User rateMedic();
    User cancelRequest();

}
