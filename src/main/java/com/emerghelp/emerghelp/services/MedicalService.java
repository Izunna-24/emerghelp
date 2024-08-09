package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.dtos.requests.AcceptOrderRequest;
import com.emerghelp.emerghelp.dtos.requests.UpgradeToMedicalPractitionerRequest;
import com.emerghelp.emerghelp.dtos.responses.AcceptOrderResponse;
import com.emerghelp.emerghelp.dtos.responses.UpdateMedicalResponse;
import com.emerghelp.emerghelp.dtos.responses.UpgradeToMedicalPractitionerResponse;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.stereotype.Service;

@Service
public interface MedicalService {

    UpgradeToMedicalPractitionerResponse upgradeToMedicalPractitioner(UpgradeToMedicalPractitionerRequest Request);
    AcceptOrderResponse acceptOrderMedic(AcceptOrderRequest request);

    Medic getMedicalPractionerById(long id);

    UpdateMedicalResponse updateMedicalPractitioner(Long medicalPractitionerId, JsonPatch jsonPatch);
}
