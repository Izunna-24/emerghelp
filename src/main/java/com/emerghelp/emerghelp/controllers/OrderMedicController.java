package com.emerghelp.emerghelp.controllers;

import com.emerghelp.emerghelp.dtos.requests.AcceptOrderMedicDTO;
import com.emerghelp.emerghelp.dtos.requests.OrderMedicDTO;
import com.emerghelp.emerghelp.dtos.responses.AcceptOrderMedicResponse;
import com.emerghelp.emerghelp.dtos.responses.OrderMedicResponse;
import com.emerghelp.emerghelp.services.MedicOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/medics")
@AllArgsConstructor
public class OrderMedicController {

        private final MedicOrderService orderMedicService;

        @PostMapping(value = "/order", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<OrderMedicResponse> orderMedic(@ModelAttribute OrderMedicDTO orderMedicDTO) {
            OrderMedicResponse response = orderMedicService.orderMedic(orderMedicDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

    @PostMapping(value = "/accept-order", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AcceptOrderMedicResponse> acceptOrder(@ModelAttribute AcceptOrderMedicDTO acceptOrderMedicDTO) {
        AcceptOrderMedicResponse response = orderMedicService.acceptOrder(acceptOrderMedicDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    }




