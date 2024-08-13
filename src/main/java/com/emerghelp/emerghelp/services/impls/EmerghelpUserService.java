package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.constants.Role;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.RequestRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.responses.MedicRequestResponse;
import com.emerghelp.emerghelp.dtos.responses.RegisterUserResponse;
import com.emerghelp.emerghelp.services.UserService;
import com.emerghelp.emerghelp.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final RequestRepository requestRepository;

    @Autowired
    public EmerghelpUserService(UserRepository userRepository,
                                ModelMapper modelMapper,
                                PasswordEncoder passwordEncoder,
                                RequestRepository requestRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.requestRepository = requestRepository;
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
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException
                        (String.format("user with email %s not found", email)));
        return user;
    }

    @Override
    public MedicRequestResponse requestMedic(Long email, MedicRequest medicRequest) {
        User user = userRepository.findById(email)
                .orElseThrow(()-> new UserNotFoundException
                        (String.format("email %s does not not exist", email)));
        return null;
    }

}

