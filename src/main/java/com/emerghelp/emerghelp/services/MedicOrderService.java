package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.models.MedicRequest;
import com.emerghelp.emerghelp.dtos.requests.MedicRequestDTO;
import com.emerghelp.emerghelp.dtos.responses.MedicRequestResponse;
import com.emerghelp.emerghelp.dtos.responses.OrderMedicHistory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface MedicOrderService {
    List<OrderMedicHistory> viewAllOrderFor(Long userId);
    MedicRequestResponse orderMedic(MedicRequestDTO medicRequestDTO);
    MedicRequest getMedicOrderBy(long id);

}
