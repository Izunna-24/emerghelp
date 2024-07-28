package com.emerghelp.emerghelp.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {
        @Id
        @GeneratedValue
        private Long id;
        private String streetName;
        private String city;
        private String state;

    }

