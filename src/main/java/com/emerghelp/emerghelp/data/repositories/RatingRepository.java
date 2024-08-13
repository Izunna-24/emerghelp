package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
List<Rating> findByMedicRatedId(Long medicId);
}
