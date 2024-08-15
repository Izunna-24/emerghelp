package com.emerghelp.emerghelp.data.models;

import com.emerghelp.emerghelp.data.constants.Gender;
import com.emerghelp.emerghelp.data.constants.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Setter
@Getter
@ToString
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private Gender gender;
    @Column(unique = true)
    private String email;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = STRING)
    private Set<Role> roles;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateRegistered;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateUpdated;

    @PrePersist
    private void setDateRegistered(){
        this.dateRegistered = LocalDateTime.now();
    }
    @PreUpdate
    private void setDateUpdated(){
        this.dateUpdated = LocalDateTime.now();
    }


}
