package com.emerghelp.emerghelp.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest {
    private Long id;
    private Long raterId;
    private Long medicRatedId;
    private String comment;
    private Integer score;
}
