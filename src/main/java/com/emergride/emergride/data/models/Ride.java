package com.emergride.emergride.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Ride {
@Id
@GeneratedValue(strategy = IDENTITY)
private Long id;
}
