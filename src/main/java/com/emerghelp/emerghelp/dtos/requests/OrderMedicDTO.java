package com.emerghelp.emerghelp.dtos.requests;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMedicDTO {
   private Long userId;
   private Long medicId;
   private String longitude;
   private String latitude;
   private String description;
}
