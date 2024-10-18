package com.dziem.WineCellarManager.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    private double ratingStars;
    private Flavour flavour;
    private String aroma;
    private int agingTime;
    private String suggestedFoodPairings;
    @OneToOne(mappedBy = "rating")
    private Wine wine;
}
