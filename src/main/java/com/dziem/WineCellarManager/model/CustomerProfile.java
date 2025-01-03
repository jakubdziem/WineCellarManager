package com.dziem.WineCellarManager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class CustomerProfile {
    private UUID id;
    private String nickname;
    private Integer numberOfWines;
    private Integer numberOfRatings;
    private Wine wineWithOldestVintage;
    private String favoriteRegion;
    private String favoriteCountry;
    private String favoriteWinery;
    private WineType favoriteWineType;
    private String priceOfCollar;
}
