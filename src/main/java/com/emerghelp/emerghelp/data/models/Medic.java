package com.emerghelp.emerghelp.data.models;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Table(name = "medic")
public class Medic {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String photoUrl;
    private String specialization;
    @Column(nullable = false,unique = true)
    private String licenseNumber;
    private Boolean isAvailable;


}
