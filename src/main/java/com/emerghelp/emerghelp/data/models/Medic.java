package com.emerghelp.emerghelp.data.models;



import jakarta.persistence.*;
import lombok.*;


import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Table(name = "medic")
public class Medic {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long practitionerId;
    private String email;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String photoUrl;
    private String specialization;
    @Column(nullable = false,unique = true)
    private String licenseNumber;
    private Boolean isAvailable;
    private String longitude;
    private String latitude;
}
