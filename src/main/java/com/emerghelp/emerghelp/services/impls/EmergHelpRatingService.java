package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.models.Rating;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.MedicRepository;
import com.emerghelp.emerghelp.data.repositories.RatingRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.RatingRequest;
import com.emerghelp.emerghelp.dtos.responses.RatingRequestResponse;
import com.emerghelp.emerghelp.exceptions.MedicNotFoundException;
import com.emerghelp.emerghelp.exceptions.UserNotFoundException;
import com.emerghelp.emerghelp.services.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmergHelpRatingService implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MedicRepository medicRepository;


    @Autowired
    public EmergHelpRatingService(RatingRepository ratingRepository,
                                  UserRepository userRepository,
                                  MedicRepository medicRepository, ModelMapper modelMapper){
        this.medicRepository = medicRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public RatingRequestResponse addRating(RatingRequest ratingRequest) {
        validateRatingRequest(ratingRequest);
        Rating rating = new Rating();
        rating.setScore(ratingRequest.getScore());
        rating.setComment(ratingRequest.getComment());
        User rater = userRepository.findById(ratingRequest.getRaterId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Medic medicRated = medicRepository.findById(ratingRequest.getMedicRatedId())
                .orElseThrow(() -> new MedicNotFoundException("Medic not found"));
        rating.setRater(rater);
        rating.setMedicRated(medicRated);

        rating = ratingRepository.save(rating);
        RatingRequestResponse response = new RatingRequestResponse();
        response.setScore(rating.getScore());
        response.setComment(rating.getComment());
        return response;
    }

    private void validateRatingRequest(RatingRequest request) {
        if (request.getScore() < 1 || request.getScore() > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }
        if (request.getComment() == null || request.getComment().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
    }



    @Override
    public List<Rating> getRatingsForMedic(Long medicId) {
        return ratingRepository.findByMedicRatedId(medicId);

    }
    public double getAverageRatingForMedic(Long medicId) {
        List<Rating> ratings = getRatingsForMedic(medicId);
        return ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);
    }
}




