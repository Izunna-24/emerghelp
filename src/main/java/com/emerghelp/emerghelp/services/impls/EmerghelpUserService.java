package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.constants.Role;
import com.emerghelp.emerghelp.data.models.Confirmation;
import com.emerghelp.emerghelp.data.models.MedicRequest;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.ConfirmationRepository;
import com.emerghelp.emerghelp.data.repositories.MedicRequestRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.requests.RequestMedicRequest;
import com.emerghelp.emerghelp.dtos.responses.RegisterUserResponse;
import com.emerghelp.emerghelp.dtos.responses.UpdateProfileResponse;
import com.emerghelp.emerghelp.dtos.responses.ViewProfileResponse;
import com.emerghelp.emerghelp.exceptions.EmailAlreadyExistException;
import com.emerghelp.emerghelp.exceptions.UserNotFoundException;
import com.emerghelp.emerghelp.services.EmailService;
import com.emerghelp.emerghelp.services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.emerghelp.emerghelp.exceptions.Exception;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import static com.emerghelp.emerghelp.data.constants.Role.USER;


@Service
public class EmerghelpUserService implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;

    private final MedicRequestRepository medicRequestRepository;



    @Autowired
    public EmerghelpUserService(UserRepository userRepository,
                                ModelMapper modelMapper,
                                PasswordEncoder passwordEncoder,
                                ConfirmationRepository confirmationRepository, EmailService emailService, MedicRequestRepository medicRequestRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationRepository = confirmationRepository;
        this.emailService = emailService;
        this.medicRequestRepository = medicRequestRepository;

    }

    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        User user = modelMapper.map(registerUserRequest, User.class);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setRoles(new HashSet<>());
        Set<Role> roles = user.getRoles();
        roles.add(USER);
        user = userRepository.save(user);
        RegisterUserResponse response = modelMapper.map(user, RegisterUserResponse.class);
        response.setMessage("your account has been created successfully");
        return response;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        String.format("user with id %d not found", id)));
    }
    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("user not found")
                );
        return user;
    }
    @Override
    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistException("Email already exists, consider logging in instead");
        }

        user.setEnabled(false);
        userRepository.save(user);
        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);
        emailService.sendSimpleMailMessage(user.getFirstName(), user.getEmail(), confirmation.getToken());
        return user;
    }
    @Override
    public Boolean verifyToken(String token) {
        try {
            Confirmation confirmation = confirmationRepository.findByToken(token);
            if (confirmation == null) {
                return Boolean.FALSE;
            }
            User user = confirmation.getUser();
            if (user == null) {
                return Boolean.FALSE;
            }
            user.setEnabled(true);
            userRepository.save(user);
            return Boolean.TRUE;
        } catch (DataAccessException e) {
            System.err.println("Error accessing data: " + e.getMessage());
            return Boolean.FALSE;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return Boolean.FALSE;
        }
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception
                        (String.format("user with email %s not found", email)));
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

    @Override
    public List<MedicRequest> viewAllRequests(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found"));
//     return emergencyRequestRepository.findByUserId(id);
        return medicRequestRepository.findEmergencyRequestByUser(user);

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
