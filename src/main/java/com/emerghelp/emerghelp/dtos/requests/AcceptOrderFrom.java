package com.emerghelp.emerghelp.dtos.requests;

import com.emerghelp.emerghelp.data.models.User;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcceptOrderFrom {
    private Long id;
    private User user;
    private String photoUrl;
    private String specialization;
    private String licenseNumber;
    private Boolean isAvailable;
}
