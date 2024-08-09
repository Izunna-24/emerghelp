package com.emerghelp.emerghelp.dtos.requests;

import com.emerghelp.emerghelp.data.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class AcceptOrderRequest {
    private Long id;
    private User user;
    private String photoUrl;
    private String specialization;
    private String licenseNumber;
    private Boolean isAvailable;
}
