package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.constants.Role;
import com.emerghelp.emerghelp.data.models.Admin;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.AdminRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.ActivateUserRequests;
import com.emerghelp.emerghelp.dtos.requests.DeactivateUserRequests;
import com.emerghelp.emerghelp.dtos.requests.RegisterAdminRequests;
import com.emerghelp.emerghelp.dtos.responses.ActivateUserResponse;
import com.emerghelp.emerghelp.dtos.responses.DeactivateUserResponse;
import com.emerghelp.emerghelp.dtos.responses.RegisterAdminResponse;
import com.emerghelp.emerghelp.exceptions.EmerghelpBaseException;
import com.emerghelp.emerghelp.exceptions.UserNotFoundException;
import com.emerghelp.emerghelp.services.AdminService;
import com.emerghelp.emerghelp.services.UserService;
import jakarta.mail.search.SearchTerm;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository,
                            UserService userService,
                            UserRepository userRepository,
                            ModelMapper modelMapper,
                            PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public RegisterAdminResponse registerAdmin(RegisterAdminRequests requests) {
        validateEmail(requests.getEmail());
        Admin admin = modelMapper.map(requests, Admin.class);
        admin.setPassword(passwordEncoder.encode(requests.getPassword()));
        admin.setUserName(requests.getUserName());
        admin.setRoles(new HashSet<>());
        Set<Role> roles = admin.getRoles();
        roles.add(Role.ADMIN);
        admin = adminRepository.save(admin);
        RegisterAdminResponse response = modelMapper.map(admin, RegisterAdminResponse.class);
        response.setMessage("Your account as an admin has been created successfully");
        return response;
    }

    @Override
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
               .orElseThrow(() -> new EmerghelpBaseException(String.format("Admin with id %d not found", id)));
    }

    @Override
    public DeactivateUserResponse deactivateUserAccount(DeactivateUserRequests requests) {
        User userToBeDeactivated = userRepository.findById(requests.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found or Incorrect userId"));
        userToBeDeactivated.setEnabled(false);
        userRepository.save(userToBeDeactivated);
        DeactivateUserResponse response = new DeactivateUserResponse();
        response.setMessage("User account was successfully deactivated");
        return response;

    }

    @Override
    public ActivateUserResponse activateUserAccount(ActivateUserRequests requests){
        User userToBeActivated = userRepository.findById(requests.getUserId())
               .orElseThrow(() -> new UserNotFoundException("User not found or Incorrect userId"));
        userToBeActivated.setEnabled(true);
        userRepository.save(userToBeActivated);
        ActivateUserResponse response = new ActivateUserResponse();
        response.setMessage("User account was successfully activated");
        return response;
    }

    private void validateEmail(String email) {
        if(email == null || email.trim().isEmpty()) {
            throw new EmerghelpBaseException("Email cannot be null or empty");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (!pattern.matcher(email).matches()) {
            throw new EmerghelpBaseException("Invalid email format");
        }
        boolean emailExists = adminRepository.existsByEmail(email.toLowerCase());
        if (emailExists) {
            throw new EmerghelpBaseException("Email " + email + "already exists");

        }

    }
}
