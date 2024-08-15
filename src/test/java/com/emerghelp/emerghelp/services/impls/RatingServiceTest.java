package com.emerghelp.emerghelp.services.impls;


import com.emerghelp.emerghelp.data.models.Medic;
import com.emerghelp.emerghelp.data.models.Rating;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.RatingRepository;
import com.emerghelp.emerghelp.dtos.requests.RatingRequest;
import com.emerghelp.emerghelp.dtos.responses.RatingRequestResponse;
import com.emerghelp.emerghelp.services.MedicService;
import com.emerghelp.emerghelp.services.RatingService;
import com.emerghelp.emerghelp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
public class RatingServiceTest {
    @Autowired
    private RatingService ratingService;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private MedicService medicService;
    @Autowired
    private UserService userService;


    @Transactional
    @Test
    @Rollback
    @Sql(scripts = {"/db/data.sql"})
    public void testThatMedicCanBeRated_byUser(){
       User rater = userService.getById(200L);
       Medic rated = medicService.getMedicById(300L);
       RatingRequest rating = new RatingRequest();
       rating.setRaterId(rater.getId());
       rating.setMedicRatedId(rated.getId());
       rating.setScore(3);
       rating.setComment("good service generally!");
       RatingRequestResponse response = ratingService.addRating(rating);
       log.info("rated a medic{}",response);
       Rating savedRating = ratingRepository.findAll().get(0);
       assertThat(savedRating.getComment()).isEqualTo("good service generally!");
        assertThat(savedRating.getScore()).isEqualTo(3);





    }
}
