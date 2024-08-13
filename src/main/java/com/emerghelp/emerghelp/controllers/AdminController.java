package com.emerghelp.emerghelp.controllers;

import com.emerghelp.emerghelp.dtos.requests.ActivateUserRequests;
import com.emerghelp.emerghelp.dtos.requests.DeactivateUserRequests;
import com.emerghelp.emerghelp.dtos.requests.RegisterAdminRequests;
import com.emerghelp.emerghelp.dtos.responses.ActivateUserResponse;
import com.emerghelp.emerghelp.dtos.responses.DeactivateUserResponse;
import com.emerghelp.emerghelp.dtos.responses.RegisterAdminResponse;
import com.emerghelp.emerghelp.exceptions.EmerghelpBaseException;
import com.emerghelp.emerghelp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<RegisterAdminResponse> registerAdmin(@RequestBody RegisterAdminRequests requests){
        try {
            RegisterAdminResponse response = adminService.registerAdmin(requests);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EmerghelpBaseException exception) {
            RegisterAdminResponse response = new RegisterAdminResponse();
            response.setMessage("Your account as an admin has been created");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/deactivate")
    public ResponseEntity<DeactivateUserResponse> deactivateUser(@RequestBody DeactivateUserRequests requests){
        try {
            DeactivateUserResponse response = adminService.deactivateUserAccount(requests);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EmerghelpBaseException exception) {
            DeactivateUserResponse response = new DeactivateUserResponse();
            response.setMessage("User account has been deactivated");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/activate")
    public ResponseEntity<ActivateUserResponse> activateUser(@RequestBody ActivateUserRequests requests){
        try {
            ActivateUserResponse response = adminService.activateUserAccount(requests);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EmerghelpBaseException exception) {
            ActivateUserResponse response = new ActivateUserResponse();
            response.setMessage("User account has been activated");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
