package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.models.OrderMedic;
import com.emerghelp.emerghelp.dtos.requests.OrderMedicDTO;
import com.emerghelp.emerghelp.dtos.responses.MedicRequestResponse;
import com.emerghelp.emerghelp.dtos.responses.OrderMedicHistory;

import java.util.List;

public interface MedicOrderService {
    List<OrderMedicHistory> viewAllOrderFor(Long userId);
    MedicRequestResponse orderMedic(OrderMedicDTO orderMedicDTO);
    OrderMedic getMedicOrderBy(long id);

}
