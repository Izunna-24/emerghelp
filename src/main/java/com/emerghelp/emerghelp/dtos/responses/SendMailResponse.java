package com.emerghelp.emerghelp.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMailResponse {
    private String message;
    private String subject;
    private String to;
    private String text;
    private String from;
}
