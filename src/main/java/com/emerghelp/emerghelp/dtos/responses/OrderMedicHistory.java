package com.emerghelp.emerghelp.dtos.responses;


import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.models.User;
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
    private User user;
    private Medic medic;
    private Medic pictureUrl;
    public String getName() {
        if (user != null) {
            return user.getFirstName() + " " + user.getLastName();
        }
        return "";
    }
    public String getName2() {
        if (user != null) {
            return medic.getFirstName() + " " + medic.getLastName();
        }
        return "";
    }
}

