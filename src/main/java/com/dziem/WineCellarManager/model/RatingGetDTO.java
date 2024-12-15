package com.dziem.WineCellarManager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingGetDTO {
    private Long id;
    private boolean hasRating;
    private Integer ratingStars;
    private Flavour flavour;
    private String aroma;
    private int agingTime;
    private String suggestedFoodPairings;
}
