package com.dziem.WineCellarManager.model;

import lombok.Data;

@Data
public class WineFromApiDTO {
    private String winery;
    private String wine;
    private String location;
    private String image;
    private WineType wineType;
}