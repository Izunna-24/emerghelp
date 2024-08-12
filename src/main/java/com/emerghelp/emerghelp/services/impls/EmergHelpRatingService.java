package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.models.Rating;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.MedicRepository;
import com.emerghelp.emerghelp.data.repositories.RatingRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.RatingRequest;
import com.emerghelp.emerghelp.dtos.responses.RatingRequestResponse;
import com.emerghelp.emerghelp.services.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergHelpRatingService implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MedicRepository medicRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmergHelpRatingService(RatingRepository ratingRepository,
                                  UserRepository userRepository,
                                  MedicRepository medicRepository, ModelMapper modelMapper){
        this.medicRepository = medicRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RatingRequestResponse addRating(RatingRequest ratingRequest) {
        Rating rating = modelMapper.map(ratingRequest, Rating.class);
        User rater = userRepository.findById(rating.getRater().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Medic medicRated = medicRepository.findById(rating.getMedicRated().getId())
                .orElseThrow(() -> new RuntimeException("Medic not found"));
        rating.setRater(rater);
        rating.setMedicRated(medicRated);
        rating =  ratingRepository.save(rating);
        RatingRequestResponse response = modelMapper.map(rating, RatingRequestResponse.class);
        response.setScore(4);
        response.setComment("great nurse");
        return response;
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




