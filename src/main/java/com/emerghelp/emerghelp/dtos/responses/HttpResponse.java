package com.emerghelp.emerghelp.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {
    private String timeStamp;
    private int statusCode;
    private HttpStatus status;
    private String message;
    private String DeveloperMessage;
    private String path;
    private String requestMethod;
    private Map<?, ?> data;
}