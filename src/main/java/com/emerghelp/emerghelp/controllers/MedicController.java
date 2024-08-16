package com.emerghelp.emerghelp.controllers;

import com.emerghelp.emerghelp.dtos.requests.RegisterMedicRequest;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.responses.HttpResponses;
import com.emerghelp.emerghelp.dtos.responses.RegisterMedicResponse;
import com.emerghelp.emerghelp.dtos.responses.RegisterUserResponse;
import com.emerghelp.emerghelp.services.MedicService;
import com.emerghelp.emerghelp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;


@RestController
@RequestMapping("/api/medics/create-medic")
@AllArgsConstructor
public class MedicController {
    private final MedicService medicService;

    @PostMapping
    public ResponseEntity<HttpResponses> registerMedic(@RequestBody RegisterMedicRequest request) {
        RegisterMedicResponse medic = medicService.registerMedic(request);
        return ResponseEntity.created(URI.create("")).body(
                HttpResponses.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("medic", medic))
                        .message("medic registered")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

}
