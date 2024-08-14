package com.emerghelp.emerghelp.dtos.responses;

import com.emerghelp.emerghelp.data.constants.OrderMedicStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptOrderMedicResponse {
    private Long orderId;
    private Long medicId;
    private OrderMedicStatus status;
}
