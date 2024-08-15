package com.emerghelp.emerghelp.dtos.responses;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Authorization {
    private String authorization_code;
}