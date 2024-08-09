package com.emerghelp.emerghelp.dtos.responses;



import com.emerghelp.emerghelp.data.models.Medic;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MedicRequestResponse {
    private List<Medic> availableMedic;
}
