package com.dziem.WineCellarManager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID customerId;
    private String email;
    private String password;
    private String nickname;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<Wine> wines;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Rating> ratings;
}
