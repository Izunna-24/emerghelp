package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.models.Confirmation;
import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.repositories.ConfirmationRepository;
import com.emerghelp.emerghelp.data.repositories.MedicRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.AcceptOrderMedicDTO;
import com.emerghelp.emerghelp.dtos.requests.RegisterMedicRequest;
import com.emerghelp.emerghelp.dtos.responses.AcceptOrderMedicResponse;
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
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static com.emerghelp.emerghelp.data.constants.Role.MEDIC;


@Service
public class EmergHelpMedicService implements MedicService {

    private final MedicRepository medicRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Autowired
    public EmergHelpMedicService(MedicRepository medicalServiceRepository,
                                 ModelMapper modelMapper,
                                 PasswordEncoder passwordEncoder,
                                 ConfirmationRepository confirmationRepository,
                                 EmailService emailService, UserRepository userRepository) {
        this.medicRepository = medicalServiceRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.confirmationRepository = confirmationRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @Override
    public RegisterMedicResponse register(RegisterMedicRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistException("Email already exists");
        }
        Medic medic = modelMapper.map(request, Medic.class);
        medic.setPassword(passwordEncoder.encode(request.getPassword()));
        medic.setRoles(new HashSet<>());
        medic.getRoles().add(MEDIC);
        medic.setIsEnabled(false);
        Medic savedMedic = medicRepository.save(medic);
        Confirmation confirmation = new Confirmation(savedMedic);
        emailService.sendHtmlEmail(savedMedic.getFirstName(), savedMedic.getEmail(), confirmation.getToken());
        RegisterMedicResponse response = modelMapper.map(savedMedic, RegisterMedicResponse.class);
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
            Medic medic = confirmation.getMedic();
            if (medic == null) {
                return Boolean.FALSE;
            }
            medic.setIsEnabled(true);
            medicRepository.save(medic);
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
    public AcceptOrderMedicResponse acceptOrderMedic(AcceptOrderMedicDTO request) {
        return null;
    }

    @Override
    public Medic getMedicById(long id) {
        return medicRepository.findById(id)
                .orElseThrow(()->new MedicalPractionerNotFoundException(String.format("medical practitioner with this id %d not found", id)));
    }
    @Override
    public UpdateMedicalResponse updateMedicalPractitioner(Long medicalId, JsonPatch jsonPatch) {
        try {
            Medic medicalPractitioner = getMedicById(medicalId);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode practitionerNode = objectMapper.convertValue(medicalPractitioner, JsonNode.class);
            practitionerNode = jsonPatch.apply(practitionerNode);
            medicalPractitioner = objectMapper.convertValue(practitionerNode, Medic.class);
            medicalPractitioner = medicRepository.save(medicalPractitioner);
            return modelMapper.map(medicalPractitioner, UpdateMedicalResponse.class);
        }catch (JsonPatchException exception){
            throw new MedicalPractitionerUpdateFailException(exception.getMessage());
        }
    }
}
