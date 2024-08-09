package com.emerghelp.emerghelp.controllers;

import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.responses.RegisterUserResponse;
import com.emerghelp.emerghelp.dtos.responses.UpdateProfileResponse;
import com.emerghelp.emerghelp.dtos.responses.ViewProfileResponse;
import com.emerghelp.emerghelp.exceptions.EmerghelpBaseException;
import com.emerghelp.emerghelp.services.UserService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/view/{user_id}")
    public ResponseEntity<?> viewProfile(@PathVariable("user_id") long userId){
        try{
            ViewProfileResponse response = userService.viewProfile(userId);
            return ResponseEntity.status(OK).body(response);
        }catch(EmerghelpBaseException message){
            return ResponseEntity.status(BAD_REQUEST).body(message.getMessage());
        }
    }

    @PatchMapping("/update/{user_id}")
    public ResponseEntity<?> updateProfile(@PathVariable("user_id") long userId, @RequestBody JsonPatch jsonPatch) {
        try {
            UpdateProfileResponse response = userService.updateProfile(userId, jsonPatch);
            return ResponseEntity.status(OK).body(response);
        } catch (EmerghelpBaseException message) {
            return ResponseEntity.status(BAD_REQUEST).body(message.getMessage());
        }
    }



}
