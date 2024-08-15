package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.dtos.requests.AcceptOrderFrom;
import com.emerghelp.emerghelp.dtos.requests.RegisterMedicRequest;

import com.emerghelp.emerghelp.dtos.responses.AcceptOrderResponse;
import com.emerghelp.emerghelp.dtos.responses.RegisterMedicResponse;
import com.emerghelp.emerghelp.dtos.responses.UpdateMedicalResponse;
import com.github.fge.jsonpatch.JsonPatch;
import jakarta.mail.MessagingException;


public interface MedicService {

    RegisterMedicResponse registerMedic(RegisterMedicRequest request);

    Medic getMedicalPractionerById(Long id);

    UpdateMedicalResponse updateMedicalPractitioner(Long medicalPractitionerId, JsonPatch jsonPatch);
}
