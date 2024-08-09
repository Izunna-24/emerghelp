package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.constants.Role;
import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.MedicRepository;
import com.emerghelp.emerghelp.dtos.requests.AcceptOrderRequest;
import com.emerghelp.emerghelp.dtos.requests.UpgradeToMedicalPractitionerRequest;
import com.emerghelp.emerghelp.dtos.responses.AcceptOrderResponse;
import com.emerghelp.emerghelp.dtos.responses.UpdateMedicalResponse;
import com.emerghelp.emerghelp.dtos.responses.UpgradeToMedicalPractitionerResponse;
import com.emerghelp.emerghelp.exceptions.*;
import com.emerghelp.emerghelp.services.MedicalService;
import com.emerghelp.emerghelp.services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmergHelpMedicalService implements MedicalService {

    private final MedicRepository medicRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public EmergHelpMedicalService(MedicRepository medicalServiceRepository,
                                   ModelMapper modelMapper, UserService userService) {
        this.medicRepository = medicalServiceRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public UpgradeToMedicalPractitionerResponse upgradeToMedicalPractitioner(UpgradeToMedicalPractitionerRequest request) {
        try {
            User user = userService.getUserByUsername(request.getEmail());
            user.getRoles().add(Role.MEDICAL_PRACTITIONER);
            // userRepository.save(user);
            Medic medicalPractitioner = new Medic();
            medicalPractitioner.setPhotoUrl(request.getPhotoUrl());
            medicalPractitioner.setSpecialization(request.getSpecialization());
            medicalPractitioner.setLicenseNumber(request.getLicenseNumber());
            medicalPractitioner.setEmail(request.getEmail());
            medicRepository.save(medicalPractitioner);
            UpgradeToMedicalPractitionerResponse response = modelMapper.map(medicalPractitioner, UpgradeToMedicalPractitionerResponse.class);
            response.setMessage("User upgraded to medical practitioner successfully");
            System.out.println(response);
            return response;
        } catch (UserNotFoundException e) {
            System.err.println("User not found: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error upgrading user: " + e.getMessage());
            throw new FailToUgradeUserException("Failed to upgrade user");
        }
    }

    @Override
    public AcceptOrderResponse acceptOrderMedic(AcceptOrderRequest request) {
        return null;
    }

    @Override
    public Medic getMedicalPractionerById(long id) {
        return medicRepository.findById(id)
                .orElseThrow(()->new MedicalPractionerNotFoundException(String.format("medical practitioner with this id %d not found", id)));
    }
    @Override
    public UpdateMedicalResponse updateMedicalPractitioner(Long medicalId, JsonPatch jsonPatch) {
        try {
            Medic medicalPractitioner = getMedicalPractionerById(medicalId);
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
