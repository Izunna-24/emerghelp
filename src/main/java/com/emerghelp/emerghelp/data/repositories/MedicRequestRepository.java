package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.MedicRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicRequestRepository extends JpaRepository<MedicRequest, Long> {
List<MedicRequest> viewAllRequestBy(Long userId);

    List<MedicRequest> viewAllRequestById(Long id);
}
