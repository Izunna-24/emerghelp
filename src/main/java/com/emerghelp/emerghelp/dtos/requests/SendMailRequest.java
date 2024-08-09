package com.emerghelp.emerghelp.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMailRequest {
    private String subject;
    private String message;
}
