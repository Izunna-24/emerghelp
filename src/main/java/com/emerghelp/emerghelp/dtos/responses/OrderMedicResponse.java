package com.emerghelp.emerghelp.dtos.responses;



import com.emerghelp.emerghelp.data.models.Medic;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderMedicResponse {
    private Long id;
    private List<Medic> availableMedic;
}
