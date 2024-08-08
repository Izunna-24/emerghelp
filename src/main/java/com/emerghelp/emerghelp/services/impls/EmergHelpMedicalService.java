package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.constants.Role;
import com.emerghelp.emerghelp.data.models.MedicalPractitioner;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.MedicalServiceRepository;
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

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class EmergHelpMedicalService implements MedicalService {

    private final MedicalServiceRepository medicalServiceRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public EmergHelpMedicalService(MedicalServiceRepository medicalServiceRepository,
                                   ModelMapper modelMapper, UserService userService) {
        this.medicalServiceRepository = medicalServiceRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public UpgradeToMedicalPractitionerResponse upgradeToMedicalPractitioner(UpgradeToMedicalPractitionerRequest request) {
        try {
            User user = userService.getUserByUsername(request.getEmail());
            user.getRoles().add(Role.MEDICAL_PRACTITIONER);
            // userRepository.save(user);
            MedicalPractitioner medicalPractitioner = new MedicalPractitioner();
            medicalPractitioner.setPhotoUrl(request.getPhotoUrl());
            medicalPractitioner.setSpecialization(request.getSpecialization());
            medicalPractitioner.setLicenseNumber(request.getLicenseNumber());
            medicalPractitioner.setEmail(request.getEmail());
            medicalServiceRepository.save(medicalPractitioner);
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
    public AcceptOrderResponse acceptRequest(AcceptOrderRequest orderRequest) {
        Long medicalRequestId = orderRequest.getRequestId();
        validateRequestId(medicalRequestId);
        fetchUserById(medicalRequestId);
        AcceptOrderRequest savedRequest = saveMedicalServiceRequest(medicalRequestId);

        return createAcceptOrderResponse(savedRequest);
    }
    private void validateRequestId(Long requestId) {
        if (requestId == null) {
            throw new RequestIdCannotBeNullException("Request ID cannot be null");
        }
    }
    private void fetchUserById(Long requestId) {
        User user = userService.getById(requestId);
        if (user == null) {
            throw new UserNotFoundException("User not found for the given Request ID: " + requestId);
        }
    }
    private AcceptOrderRequest saveMedicalServiceRequest(Long requestId) {
        MedicalPractitioner acceptServiceRequest = new MedicalPractitioner();
        acceptServiceRequest.setPractitionerId(requestId);
        determineAvailabilityStatus(requestId);
        return AcceptOrderRequest.builder().requestId(
                medicalServiceRepository.save(acceptServiceRequest).getPractitionerId()).build();
    }
    private AcceptOrderResponse createAcceptOrderResponse(AcceptOrderRequest savedRequest) {
        AcceptOrderResponse response = modelMapper.map(savedRequest, AcceptOrderResponse.class);
        response.setMessage("Request accepted successfully");
        return response;
    }

    private boolean determineAvailabilityStatus(Long requestId) {
        MedicalPractitioner practitioner = medicalServiceRepository.findById(requestId)
                .orElseThrow(() -> new UserNotFoundException("User not found for the given Request ID: " + requestId));
        return practitioner.getIsAvailable();
    }

    @Override
    public MedicalPractitioner getMedicalPractionerById(long id) {
        return medicalServiceRepository.findById(id)
                .orElseThrow(()->new MedicalPractionerNotFoundException(String.format("medical practitioner with this id %d not found", id)));
    }
    @Override
    public UpdateMedicalResponse updateMedicalPractitioner(Long medicalId, JsonPatch jsonPatch) {
        try {
            MedicalPractitioner medicalPractitioner = getMedicalPractionerById(medicalId);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode practitionerNode = objectMapper.convertValue(medicalPractitioner, JsonNode.class);
            practitionerNode = jsonPatch.apply(practitionerNode);
            medicalPractitioner = objectMapper.convertValue(practitionerNode, MedicalPractitioner.class);
            medicalPractitioner = medicalServiceRepository.save(medicalPractitioner);
            return modelMapper.map(medicalPractitioner, UpdateMedicalResponse.class);
        }catch (JsonPatchException exception){
            throw new MedicalPractitionerUpdateFailException(exception.getMessage());
        }
    }
}
