package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.dtos.requests.AcceptOrderMedicDTO;
import com.emerghelp.emerghelp.dtos.requests.RegisterMedicRequest;

import com.emerghelp.emerghelp.dtos.responses.AcceptOrderMedicResponse;
import com.emerghelp.emerghelp.dtos.responses.RegisterMedicResponse;
import com.emerghelp.emerghelp.dtos.responses.UpdateMedicalResponse;
import com.github.fge.jsonpatch.JsonPatch;



public interface MedicService {

    RegisterMedicResponse register(RegisterMedicRequest request);

    Boolean verifyToken(String token);

    AcceptOrderMedicResponse acceptOrderMedic(AcceptOrderMedicDTO request);

    Medic getMedicalPractionerById(long id);

    UpdateMedicalResponse updateMedicalPractitioner(Long medicalPractitionerId, JsonPatch jsonPatch);
}
