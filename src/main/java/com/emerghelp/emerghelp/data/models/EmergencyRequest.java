package com.emerghelp.emerghelp.data.models;


import com.emerghelp.emerghelp.data.constants.RequestStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;

@Entity
@Setter
@Getter
@Table(name = "emergency_request")
public class EmergencyRequest {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private String description;
    @OneToOne
    private Address address;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime requestTime;
    @Enumerated(value = STRING)
    private RequestStatus requestStatus;
    @ManyToOne
    @JoinColumn(name = "medic_id")
    private Medic medic;
    @OneToOne
    private MedicalReport medicalReport;

    @PrePersist
    private void setRequestTime(){
        requestTime = now();
    }
}
