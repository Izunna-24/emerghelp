package com.emerghelp.emerghelp.data.models;


import com.emerghelp.emerghelp.data.constants.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Table(name = "medical_practitioner")
public class MedicalPractitioner {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String specialization;
    @Column(nullable = false,unique = true)
    private String licenseNumber;
    private Boolean isAvailable;


}
