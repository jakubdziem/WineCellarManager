package com.dziem.WineCellarManager.controller;

import com.dziem.WineCellarManager.model.*;
import com.dziem.WineCellarManager.repository.CustomerRepository;
import com.dziem.WineCellarManager.repository.RoleRepository;
import com.dziem.WineCellarManager.security.CookieService;
import com.dziem.WineCellarManager.security.JWTGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static com.dziem.WineCellarManager.security.SecurityConstants.AUTHORIZATION_COOKIE_NAME;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final CookieService cookieService;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        if(customerRepository.existsByNickname(registerDTO.getNickname())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        } else {
            Customer customer = new Customer();
            customer.setEmail(registerDTO.getEmail());
            customer.setNickname(registerDTO.getNickname());
            customer.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

            Role roles = roleRepository.findByName("USER").get();
            customer.setRoles(Collections.singletonList(roles));

            customerRepository.save(customer);
            return new ResponseEntity<>("User registered success!", HttpStatus.OK);
        }
    }
    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getNickname(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        Cookie cookie = new Cookie(AUTHORIZATION_COOKIE_NAME, token);
        cookieService.setAuthorizationCookieAttributes(cookie);
        cookie.setMaxAge(24 * 60 * 60); // 1 day (optional)

        response.addCookie(cookie);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
    @PostMapping("logout")
    public void logout(HttpServletResponse response, HttpServletRequest request) {
        cookieService.deleteCookies(response, request);
    }
}
