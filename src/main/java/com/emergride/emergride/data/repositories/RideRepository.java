package com.emergride.emergride.data.repositories;

import com.emergride.emergride.data.models.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {
}
