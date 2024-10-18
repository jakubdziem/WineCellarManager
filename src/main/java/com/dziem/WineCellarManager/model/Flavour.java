package com.dziem.WineCellarManager.model;

import lombok.Getter;

@Getter
public enum Flavour {
    FRUITY("Rich in fruit flavors"),
    OAKY("Aged in oak barrels"),
    SPICY("Hints of spices"),
    EARTHY("Rich in earthy notes"),
    FLORAL("Floral aroma"),
    CITRUS("Citrus notes"),
    MINERAL("Mineral-rich"),
    NUTTY("Nutty aroma");

    private final String description;

    Flavour(String description) {
        this.description = description;
    }

}
