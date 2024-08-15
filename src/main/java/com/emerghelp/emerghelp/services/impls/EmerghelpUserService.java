package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.ConfirmationRepository;
import com.emerghelp.emerghelp.data.repositories.OrderMedicRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.LoginRequest;
import com.emerghelp.emerghelp.dtos.requests.LogoutRequest;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.responses.*;
import com.emerghelp.emerghelp.exceptions.EmailAlreadyExistException;
import com.emerghelp.emerghelp.exceptions.EmerghelpBaseException;
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

import java.util.HashSet;
import java.util.Optional;

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
        user.setRoles(new HashSet<>());
        user.getRoles().add(USER);
        user.setEnabled(false);
        user.setLoggedIn(false);
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

    @Override
    public LoginResponse login(LoginRequest request) {
        System.out.println(request);
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            User qualifiedUser = optionalUser.get();
            if (qualifiedUser.getPassword().equals(request.getPassword())) {
                qualifiedUser.setLoggedIn(true);
                LoginResponse response = new LoginResponse();
                response.setMessage("Login successful");
                return response;

            }
        }
        throw new EmerghelpBaseException("Invalid email or password");
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        Optional<User> user = userRepository.findByEmail(logoutRequest.getEmail());
        if (user.isPresent()) {
            User userToLoggedOut = user.get();
            userToLoggedOut.setLoggedIn(false);
            userRepository.save(userToLoggedOut);
            LogoutResponse response = new LogoutResponse();
            response.setMessage("Logout successful");
            return response;
        }
        throw new EmerghelpBaseException("User not found");


    }





}
