package com.gamemini.api.mappers;

import com.gamemini.api.dtos.responses.review.ReviewData;
import com.gamemini.api.entities.Review;

public class ReviewMapper {

    public static ReviewData toReviewData(Review review) {
        return ReviewData.builder()
                .title(review.getTitle())
                .content(review.getContent())
                .pokemon(review.getPokemon())
                .user(review.getUser())
                .build();
    }
}


