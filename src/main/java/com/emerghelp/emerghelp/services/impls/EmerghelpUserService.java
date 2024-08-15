package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.ConfirmationRepository;
import com.emerghelp.emerghelp.data.repositories.OrderMedicRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import static com.emerghelp.emerghelp.data.constants.Role.USER;


@Service
public class EmerghelpUserService implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final OrderMedicRepository orderMedicRepository;



    @Autowired
    public EmerghelpUserService(UserRepository userRepository,
                                ModelMapper modelMapper,
                                PasswordEncoder passwordEncoder,
                                EmailService emailService, OrderMedicRepository orderMedicRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.orderMedicRepository = orderMedicRepository;

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
        User savedUser = userRepository.save(user);
        emailService.sendHtmlEmail(savedUser.getFirstName(), savedUser.getEmail());
        RegisterUserResponse response = modelMapper.map(savedUser, RegisterUserResponse.class);
        response.setMessage("Your account has been created successfully");
        return response;
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
