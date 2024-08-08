package com.emerghelp.emerghelp.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MedicRequestDTO {
   private Long userId;
   private String longitude;
   private String latitude;
   private String description;
}
