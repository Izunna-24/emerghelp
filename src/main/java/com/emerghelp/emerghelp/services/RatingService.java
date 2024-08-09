package com.emerghelp.emerghelp.services;


import com.emerghelp.emerghelp.data.models.Rating;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RatingService {
    Rating addRating(Rating rating);
    List<Rating> getRatingsForMedic(Long medicId);

}
