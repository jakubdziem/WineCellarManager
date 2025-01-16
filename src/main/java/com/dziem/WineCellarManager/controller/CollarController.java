package com.dziem.WineCellarManager.controller;

import com.dziem.WineCellarManager.service.CustomerService;
import com.dziem.WineCellarManager.service.WineService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.dziem.WineCellarManager.security.SecurityConstants.AUTHORIZATION_COOKIE_NAME;
import static com.dziem.WineCellarManager.security.SecurityConstants.JWT_SECRET;

@Controller
@RequiredArgsConstructor
public class CollarController {
    public static final String DEFAULT_SORTING_MODE = "Wine type";
    private final WineService wineService;
    private final CustomerService customerService;

    @GetMapping("/otherCollars/{nickname}")
    public String getCollarPagePublic(Model model, @PathVariable String nickname) {
        model.addAttribute("public", true);
        model.addAttribute("found", false);
        if(customerService.customerExist(nickname)) {
            model.addAttribute("found", true);
            model.addAttribute("wines", wineService.groupWinesByType(nickname));
            model.addAttribute("customer", customerService.getCustomer(nickname));
        }
        return "otherCollars";
    }
    @GetMapping("/collar")
    public String getCollarPage(Model model, HttpServletRequest request, @RequestParam(required = false) String selectedSorting) {
        Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(AUTHORIZATION_COOKIE_NAME)).findFirst().ifPresentOrElse(
                cookie -> {
                    model.addAttribute("public", false);
                    Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .build()
                            .parseClaimsJws(cookie.getValue())
                            .getBody();
                    String nickname = claims.getSubject();
                    if(selectedSorting != null) {
                        model.addAttribute("selectedSorting", selectedSorting);
                    }
                    else {
                        model.addAttribute("selectedSorting", "Wine type");
                    }
                    String mode = (String) model.getAttribute("selectedSorting");
                    model.addAttribute("customer", customerService.getCustomer(nickname));
                    if(DEFAULT_SORTING_MODE.equals(mode)) {
                        model.addAttribute("wines", wineService.groupWinesByType(nickname));
                    } else {
                        model.addAttribute("wines", wineService.sortWinesByMode(nickname, mode));
                    }
                    },
                () -> {
                    model.addAttribute("public", false);
                    model.addAttribute("customer", customerService.getCustomer("tester"));
                    if (selectedSorting != null) {
                        model.addAttribute("selectedSorting", selectedSorting);
                    }
                    else {
                        model.addAttribute("selectedSorting", "Wine type");
                    }
                    String mode = (String) model.getAttribute("selectedSorting");
                    if(DEFAULT_SORTING_MODE.equals(mode)) {
                        model.addAttribute("wines", wineService.groupWinesByType("tester"));
                    } else {
                        model.addAttribute("wines", wineService.sortWinesByMode("tester", mode));
                    }
                });
        return "collar";
    }
}
