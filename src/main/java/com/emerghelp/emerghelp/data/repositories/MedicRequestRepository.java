package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.MedicRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MedicRequestRepository extends JpaRepository<MedicRequest, Long> {
List<MedicRequest> viewAllRequestById(Long id);
}
