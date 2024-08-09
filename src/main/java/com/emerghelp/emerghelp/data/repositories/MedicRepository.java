package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Medic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MedicRepository extends JpaRepository<Medic, Long> {
//    Optional<Medic> findAvailableMedic();



}
