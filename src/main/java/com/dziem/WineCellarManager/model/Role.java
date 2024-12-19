package com.dziem.WineCellarManager.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}