package com.emerghelp.emerghelp.dtos.responses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse {
    private Object data;
    private Boolean isSuccessful;
}
