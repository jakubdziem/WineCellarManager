package com.dziem.WineCellarManager.model;

import lombok.Data;

import java.time.Year;
@Data
public class WinePostDTO {
    private Long userId;
    private String name;
    private Year vintage;
    private String imageUrl;
    private String country;
    private String region;
    private String winery;
    private String price;
    private WineType wineType;
}
