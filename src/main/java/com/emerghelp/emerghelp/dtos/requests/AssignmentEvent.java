package com.emerghelp.emerghelp.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentEvent {
    private Long requestId;
    private boolean isAvailable;
}
