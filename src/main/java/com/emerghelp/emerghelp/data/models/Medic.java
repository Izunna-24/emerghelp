package com.emerghelp.emerghelp.data.models;



import com.emerghelp.emerghelp.data.constants.Role;
import jakarta.persistence.*;
import lombok.*;


import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Table(name = "medic")
public class Medic {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private String specialization;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = STRING)
    private Set<Role> roles;
    @Column(nullable = false,unique = true)
    private String licenseNumber;
    private Boolean isAvailable;
    private String longitude;
    private String latitude;
    private Boolean isEnabled;
}
