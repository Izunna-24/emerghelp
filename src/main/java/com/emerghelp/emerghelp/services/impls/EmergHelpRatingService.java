package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.models.Rating;
import com.emerghelp.emerghelp.data.repositories.MedicRepository;
import com.emerghelp.emerghelp.data.repositories.RatingRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergHelpRatingService implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MedicRepository medicRepository;

    @Autowired
    public EmergHelpRatingService(RatingRepository ratingRepository,
                                  UserRepository userRepository,
                                  MedicRepository medicRepository){
        this.medicRepository = medicRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Rating addRating(Rating rating) {
        return null;
    }

    @Override
    public List<Rating> getRatingsForMedic(Long medicId) {
        return List.of();
    }
}
