package com.emerghelp.emerghelp.data.models;

import com.emerghelp.emerghelp.data.constants.Gender;
import com.emerghelp.emerghelp.data.constants.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {
@Id
@GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    private String password;
    private Gender gender;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = STRING)
    private Set<Role> roles;
    private boolean isEnabled;
}
