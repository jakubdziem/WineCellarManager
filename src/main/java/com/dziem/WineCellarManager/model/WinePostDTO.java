package com.dziem.WineCellarManager.model;

import lombok.Data;

import java.time.Year;
import java.util.UUID;

@Data
public class WinePostDTO {
    private UUID customerId;
    private String name;
    private Year vintage;
    private String imageUrl;
    private String country;
    private String region;
    private String winery;
    private String price;
    private WineType wineType;
}
