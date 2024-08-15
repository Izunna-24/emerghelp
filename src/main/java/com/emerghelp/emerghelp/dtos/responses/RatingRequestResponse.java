package com.emerghelp.emerghelp.dtos.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequestResponse {
        private Long id;
        private String comment;
        private Integer score;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime timeRated;
    }

