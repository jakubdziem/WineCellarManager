package com.dziem.WineCellarManager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Customer customer;
    private double ratingStars;
    private Flavour flavour;
    private String aroma;
    private int agingTime;
    private String suggestedFoodPairings;
    @OneToOne(mappedBy = "rating")
    @JsonIgnore
    private Wine wine;
}
