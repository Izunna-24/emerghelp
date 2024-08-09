package com.emerghelp.emerghelp.services;


import com.emerghelp.emerghelp.data.models.Rating;

import java.util.List;

public interface RatingService {
    Rating addRating(Rating rating);
    List<Rating> getRatingsForMedic(Long medicId);

}
