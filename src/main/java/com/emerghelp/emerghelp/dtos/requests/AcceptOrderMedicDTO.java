package com.emerghelp.emerghelp.dtos.requests;

import com.emerghelp.emerghelp.data.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AcceptOrderMedicDTO {
   private Long orderId;
   private Long medicId;
}
