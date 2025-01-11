package com.dziem.WineCellarManager.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 3600000; //1hour test perpouses
    public static final SecretKey JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public static final String AUTHORIZATION_COOKIE_NAME = "Authorization";

}
