package com.dziem.WineCellarManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}