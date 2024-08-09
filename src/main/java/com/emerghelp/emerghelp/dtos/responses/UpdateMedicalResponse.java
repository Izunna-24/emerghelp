package com.emerghelp.emerghelp.dtos.responses;

import com.emerghelp.emerghelp.data.constants.Role;
import com.emerghelp.emerghelp.data.models.User;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class UpdateMedicalResponse {

    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private String photoUrl;
    private String specialization;
    @Column(nullable = false,unique = true)
    private String licenseNumber;
    private Boolean isAvailable;
    private Role role;
    @Setter(AccessLevel.NONE)
    private LocalDateTime timeCreated;
    @ManyToOne
    private User user;
    @PrePersist
    private void setTimeCreated() {
        this.timeCreated = LocalDateTime.now();
    }
}
