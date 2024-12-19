package com.dziem.WineCellarManager.model;

import lombok.Data;

@Data
public class RegisterDTO {
    private String nickname;
    private String email;
    private String password;
}
