package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.EmergencyRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyRequestRepository extends JpaRepository<EmergencyRequest, Long> {
}
