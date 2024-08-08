package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.constants.Role;

import com.emerghelp.emerghelp.data.models.Confirmation;
import com.emerghelp.emerghelp.data.models.User;

import com.emerghelp.emerghelp.data.repositories.ConfirmationRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.responses.RegisterUserResponse;

import com.emerghelp.emerghelp.exceptions.EmailAlreadyExistException;
import com.emerghelp.emerghelp.services.EmailService;
import com.emerghelp.emerghelp.services.UserService;
import com.emerghelp.emerghelp.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.emerghelp.emerghelp.data.constants.Role.USER;

@Service
public class EmerghelpUserService implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;

    @Autowired
    public EmerghelpUserService(UserRepository userRepository,
                                ModelMapper modelMapper,
                                PasswordEncoder passwordEncoder, ConfirmationRepository confirmationRepository, EmailService emailService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationRepository = confirmationRepository;
        this.emailService = emailService;
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
                .orElseThrow(() -> new UserNotFoundException(
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
}


