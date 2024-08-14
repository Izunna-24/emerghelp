package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.OrderMedic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderMedicRepository extends JpaRepository<OrderMedic, Long> {
    @Query("SELECT o from OrderMedic o where o.user.id=:userId")
    List<OrderMedic> findMedicRequestByUserId(Long userId);

}
