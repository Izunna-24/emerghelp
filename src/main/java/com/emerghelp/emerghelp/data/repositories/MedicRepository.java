package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Medic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicRepository extends JpaRepository<Medic,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByLicenseNumber(String licenseNumber);
    Medic findByEmailIgnoreCase(String email);

}
