package com.emerghelp.emerghelp.services;


import com.emerghelp.emerghelp.data.models.Rating;
import com.emerghelp.emerghelp.dtos.requests.RatingRequest;
import com.emerghelp.emerghelp.dtos.responses.RatingRequestResponse;

import java.util.List;

public interface RatingService {
    RatingRequestResponse addRating(RatingRequest ratingRequest);
    List<Rating> getRatingsForMedic(Long medicId);

}
