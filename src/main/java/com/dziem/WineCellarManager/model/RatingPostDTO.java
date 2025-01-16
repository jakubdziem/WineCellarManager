package com.dziem.WineCellarManager.model;

import com.dziem.WineCellarManager.validation.EnumValidator;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RatingPostDTO {

    private Long wineId;

    @NotNull(message = "Stars are mandatory")
    @Min(value = 1, message = "Stars must be between 1 and 5.")
    @Max(value = 5, message = "Stars must be between 1 and 5.")
    private Integer ratingStars;

    @NotBlank(message = "Flavour is mandatory")
    @EnumValidator(enumClazz = Flavour.class, message = "Flavour must be one of FRUITY, OAKY, SPICY, EARTHY, FLORAL, CITRUS, MINERAL, NUTTY")
    private String flavour;

    @NotBlank(message="Aroma is mandatory")
    @Size(min = 3, max = 100, message = "Aroma description must be in range from 3 to 100")
    private String aroma;

    @NotNull(message = "Aging Time is mandatory")
    @Min(value = 0, message = "Aging Time must be a number between 0 and 1000.")
    @Max(value = 1000, message = "Aging Time must be a number between 0 and 1000.")
    private Integer agingTime;

    @NotBlank(message = "Suggested Food Pairings is mandatory")
    @Size(min = 3, max = 100, message = "Suggested Food Pairings description must be in range from 3 to 100")
    private String suggestedFoodPairings;
}
