package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Confirmation findByToken(String token);
}