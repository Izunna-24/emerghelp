package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.MedicalPractitioner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalPractitionerRepository extends JpaRepository<MedicalPractitioner,Long> {
}
