package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Medic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicRepository extends JpaRepository<Medic,Long> {
}
