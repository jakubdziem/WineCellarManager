package com.dziem.WineCellarManager.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import static com.dziem.WineCellarManager.security.SecurityConstants.AUTHORIZATION_COOKIE_NAME;

@Service
public class CookieServiceImpl implements CookieService {
    @Override
    public void deleteCookies(HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        boolean haveAuthCookie = false;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(AUTHORIZATION_COOKIE_NAME)) {
                haveAuthCookie = true;
            }
        }
        if(haveAuthCookie) {
            Cookie cookie = new Cookie(AUTHORIZATION_COOKIE_NAME, null);
            setAuthorizationCookieAttributes(cookie);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
    @Override
    public void setAuthorizationCookieAttributes(Cookie cookie) {
        cookie.setHttpOnly(true); // HttpOnly flag
        cookie.setSecure(true); // Only send over HTTPS
        cookie.setPath("/"); // Cookie is available to the entire app
    }
}
