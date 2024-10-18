package com.dziem.WineCellarManager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Year;

@Data
@Entity
public class Wine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    private BigDecimal price;
    private Year vintage;
    private WineType wineType;
    private String region;
    private String winery;

    @OneToOne
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    private Rating rating;
}
