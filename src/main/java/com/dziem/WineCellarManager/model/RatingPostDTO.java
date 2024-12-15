package com.dziem.WineCellarManager.model;

import lombok.Data;

@Data
public class RatingPostDTO {
    private Long wineId;
    private Integer ratingStars;
    private Flavour flavour;
    private String aroma;
    private int agingTime;
    private String suggestedFoodPairings;
}
