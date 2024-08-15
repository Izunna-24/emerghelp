package com.emerghelp.emerghelp.dtos.requests;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AcceptOrderMedicDTO {
   private Long orderId;
   private Long medicId;
   private Long userId;

}
