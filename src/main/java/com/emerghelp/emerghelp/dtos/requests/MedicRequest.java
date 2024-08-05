package com.emerghelp.emerghelp.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicRequest {
   private Long userId;
   private LocationRequest location;
   private String type;
}
