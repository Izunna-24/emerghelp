package com.emerghelp.emerghelp.dtos.responses;


import com.emerghelp.emerghelp.data.models.Medic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class OrderMedicHistory {
    private Long orderId;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime requestTime;
    private String latitude;
    private String longitude;
    private String requestEndTime;
    private BigDecimal amountPaid;
    private Medic medic;
    private Medic pictureUrl;
    public String getName() {
        if (medic != null) {
            return medic.getUser().getFirstName() + " " + medic.getUser().getLastName();
        }
        return "";
    }
}

