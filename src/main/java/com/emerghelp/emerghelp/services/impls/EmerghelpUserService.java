package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.constants.Role;
import com.emerghelp.emerghelp.data.models.EmergencyRequest;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.EmergencyRequestRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.requests.RequestMedicRequest;
import com.emerghelp.emerghelp.dtos.responses.RegisterUserResponse;
import com.emerghelp.emerghelp.dtos.responses.UpdateProfileResponse;
import com.emerghelp.emerghelp.dtos.responses.ViewProfileResponse;
import com.emerghelp.emerghelp.services.UserService;
import com.emerghelp.emerghelp.exceptions.UserNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.emerghelp.emerghelp.data.constants.Role.USER;

@Service
public class EmerghelpUserService implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private final EmergencyRequestRepository emergencyRequestRepository;

    @Autowired
    public EmerghelpUserService(UserRepository userRepository,
                                ModelMapper modelMapper,
                                PasswordEncoder passwordEncoder, EmergencyRequestRepository emergencyRequestRepository){
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emergencyRequestRepository = emergencyRequestRepository;
    }


    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
    User user = modelMapper.map(registerUserRequest, User.class);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setRoles(new HashSet<>());
         Set<Role> roles = user.getRoles();
         roles.add(USER);
         user = userRepository.save(user);
         RegisterUserResponse response = modelMapper.map(user,RegisterUserResponse.class);
         response.setMessage("your account has been created successfully");
        return response;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(
                String.format("user with id %d not found",id)));
    }

    @Override
    public User getUserByUsername(String username) {
    User user = userRepository.findByEmail(username)
            .orElseThrow(()-> new UserNotFoundException("user not found")
        );
        return user;
    }

    @Override
    public ViewProfileResponse viewProfile(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }

        User user = userOptional.get();
        ViewProfileResponse viewProfileResponse = new ViewProfileResponse();
        viewProfileResponse.setId(user.getId());
        viewProfileResponse.setEmail(user.getEmail());
        viewProfileResponse.setFirstName(user.getFirstName());
        viewProfileResponse.setLastName(user.getLastName());
        viewProfileResponse.setPhoneNumber(user.getPhoneNumber());
        viewProfileResponse.setGender(user.getGender());

        return viewProfileResponse;
    }



    @Override
    public UpdateProfileResponse updateProfile(Long id, JsonPatch jsonPatch) {
        try{
            User user = getById(id);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode userNode = objectMapper.convertValue(user, JsonNode.class);
            userNode = jsonPatch.apply(userNode);
            user = objectMapper.convertValue(userNode, User.class);
            user = userRepository.save(user);
            return modelMapper.map(user, UpdateProfileResponse.class);



        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public User requestMedic(RequestMedicRequest requestMedicRequest) {
        return null;
    }

    public List<EmergencyRequest> viewAllRequests(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found"));
//        return emergencyRequestRepository.findByUserId(id);
        return emergencyRequestRepository.findEmergencyRequestByUser(user);

    }

    @Override
    public User getRequestById(Long id) {
        return null;
    }

    @Override
    public User rateMedic() {
        return null;
    }


    @Override
    public User cancelRequest() {
        return null;
    }
}

