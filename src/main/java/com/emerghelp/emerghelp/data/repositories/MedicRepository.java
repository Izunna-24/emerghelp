package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Medic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MedicRepository extends JpaRepository<Medic, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByLicenseNumber(String licenseNumber);
    Medic findByEmailIgnoreCase(String email);

    @Query("SELECT m FROM Medic m WHERE " +
            "ACOS(SIN(RADIANS(:latitude)) * SIN(RADIANS(m.latitude)) + " +
            "COS(RADIANS(:latitude)) * COS(RADIANS(m.latitude)) * " +
            "COS(RADIANS(:longitude) - RADIANS(m.longitude))) * 6371 < :distance")
    List<Medic> findAvailableMedicsWithinDistance(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("distance") double distance);
}
