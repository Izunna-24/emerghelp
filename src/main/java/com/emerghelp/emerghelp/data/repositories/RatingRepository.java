package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
