package com.emerghelp.emerghelp.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "progress_reports")
public class ProgressReport {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String description;
    private LocalDateTime reportTime;
    @ManyToOne
    private MedicalReport medicalReport;
}
