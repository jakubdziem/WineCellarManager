package com.dziem.WineCellarManager.model;

import lombok.Data;

import java.time.Year;

@Data
public class WineDTO {
    private Long id;
    private String price;
    private Year vintage;
    private WineType wineType;
    private String country;
    private String region;
    private String winery;
    private String name;
    private String imageUrl;
    private Rating rating;
}
