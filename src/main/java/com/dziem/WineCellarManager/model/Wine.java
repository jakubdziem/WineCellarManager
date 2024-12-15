package com.dziem.WineCellarManager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;
import java.util.Optional;

@Data
@Entity
public class Wine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;
    private String price;
    private Year vintage;
    @Enumerated(EnumType.STRING)
    private WineType wineType;
    private String country;
    private String region;
    private String winery;
    private String name;
    private String imageUrl;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    private Rating rating;
}
