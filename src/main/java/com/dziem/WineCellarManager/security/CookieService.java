package com.dziem.WineCellarManager.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    void deleteCookies(HttpServletResponse response, HttpServletRequest request);
    void setAuthorizationCookieAttributes(Cookie cookie);

}
