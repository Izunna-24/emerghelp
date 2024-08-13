package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.models.Confirmation;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.ConfirmationRepository;
import com.emerghelp.emerghelp.data.repositories.MedicRequestRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
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
    public RegisterUserResponse register(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail().toLowerCase().strip())) {
            throw new EmailAlreadyExistException("Email already exists, consider logging in instead");
        }
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(new HashSet<>());
        user.getRoles().add(USER);
        user.setEnabled(false);
        User savedUser = userRepository.save(user);
        Confirmation confirmation = new Confirmation(savedUser);
        emailService.sendHtmlEmail(savedUser.getFirstName(), savedUser.getEmail(), confirmation.getToken());

        RegisterUserResponse response = modelMapper.map(savedUser, RegisterUserResponse.class);
        response.setMessage("Your account has been created successfully");
        return response;
    }

    @Override
    public Boolean verifyToken(String token) {
        try {
            Confirmation confirmation = confirmationRepository.findByToken(token);
            if (confirmation == null) {
                return Boolean.FALSE;
            }
            User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
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

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("user with id %d not found", id)));
    }

       public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("user with email %s not found", email)));
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
}
