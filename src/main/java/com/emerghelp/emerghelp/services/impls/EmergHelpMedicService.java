package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.repositories.MedicRepository;
import com.emerghelp.emerghelp.dtos.requests.RegisterMedicRequest;
import com.emerghelp.emerghelp.dtos.responses.RegisterMedicResponse;
import com.emerghelp.emerghelp.dtos.responses.UpdateMedicalResponse;
import com.emerghelp.emerghelp.exceptions.*;
import com.emerghelp.emerghelp.services.EmailService;
import com.emerghelp.emerghelp.services.MedicService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;


import static com.emerghelp.emerghelp.data.constants.Role.MEDIC;


@Service
public class EmergHelpMedicService implements MedicService {

    private final MedicRepository medicRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public EmergHelpMedicService(MedicRepository medicalServiceRepository,
                                 ModelMapper modelMapper,
                                 PasswordEncoder passwordEncoder,
                                 EmailService emailService) {
        this.medicRepository = medicalServiceRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public RegisterMedicResponse registerMedic(RegisterMedicRequest request) {
        if (medicRepository.existsByEmail(request.getEmail().toLowerCase().strip())) {
            throw new EmailAlreadyExistException("Email already exists, consider logging in instead");
        }
        if (medicRepository.existsByLicenseNumber(request.getLicenseNumber().strip())) {
            throw new LicenseNumberAlreadyExistException("License Number already exists");
        }
        Medic medic = modelMapper.map(request, Medic.class);
        medic.setPassword(passwordEncoder.encode(request.getPassword()));
        medic.setRoles(new HashSet<>());
        medic.getRoles().add(MEDIC);
        Medic savedmedic = medicRepository.save(medic);
        emailService.sendHtmlEmailToMedic(savedmedic.getFirstName(), savedmedic.getEmail());
        RegisterMedicResponse response = modelMapper.map(savedmedic, RegisterMedicResponse.class);
        response.setMessage("Your account has been created successfully");
        return response;
    }

    @Override
    public Medic getMedicById(long id){
       return medicRepository.findById(id)
           .orElseThrow(() -> new UserNotFoundException(String.format("user with id %d not found", id)));
        }

        @Override
        public UpdateMedicalResponse updateMedicalPractitioner (Long medicalId, JsonPatch jsonPatch){
            try {
                Medic medicalPractitioner = getMedicById(medicalId);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode practitionerNode = objectMapper.convertValue(medicalPractitioner, JsonNode.class);
                practitionerNode = jsonPatch.apply(practitionerNode);
                medicalPractitioner = objectMapper.convertValue(practitionerNode, Medic.class);
                medicalPractitioner = medicRepository.save(medicalPractitioner);
                return modelMapper.map(medicalPractitioner, UpdateMedicalResponse.class);
            } catch (JsonPatchException exception) {
                throw new MedicalPractitionerUpdateFailException(exception.getMessage());
            }
        }
    }

