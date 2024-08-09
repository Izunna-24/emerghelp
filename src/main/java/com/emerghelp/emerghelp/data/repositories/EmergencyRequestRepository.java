package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.EmergencyRequest;
import com.emerghelp.emerghelp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmergencyRequestRepository extends JpaRepository<EmergencyRequest, Long> {

//    @Query("SELECT a FROM EmergencyRequest  a WHERE a.user.id=:id")
//    List<EmergencyRequest> findByUserId(Long id);
    List<EmergencyRequest> findEmergencyRequestByUser(User user);
}
