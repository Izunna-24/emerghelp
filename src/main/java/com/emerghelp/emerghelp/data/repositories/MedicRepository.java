package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MedicRepository extends JpaRepository<Medic,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByLicenseNumber(String licenseNumber);
    Medic findByEmailIgnoreCase(String email);

}
