package com.dziem.WineCellarManager.controller;

import com.dziem.WineCellarManager.model.*;
import com.dziem.WineCellarManager.service.CustomerService;
import com.dziem.WineCellarManager.service.RatingService;
import com.dziem.WineCellarManager.service.WineService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final RatingService ratingService;
    private final CustomerService customerService;
    @GetMapping("/collar")
    public String getCollarPage(Model model, HttpServletRequest request, @RequestParam(required = false) String selectedSorting) {
        Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(AUTHORIZATION_COOKIE_NAME)).findFirst().ifPresentOrElse(
                cookie -> {
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
//                    throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect.");
                    //throw is commented for quicker testing of collar page
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
    @GetMapping("/viewWine")
    @ResponseBody
    public WineDTO getClickedWineById(@RequestParam("id") Long id) {
        return wineService.getClickedWineById(id);
    }
    @PutMapping(value = "/updateWine", consumes = "application/json")
    public ResponseEntity<Void> updateClickedWineById(@RequestBody WineDTO wine) {
        if(wineService.updateClickedWineById(wine)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteWine")
    public ResponseEntity<Void> deleteClickedWineById(@RequestParam Long id) {
        if(wineService.deleteClickedWineById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getWineRating")
    @ResponseBody
    public ResponseEntity<RatingGetDTO> getWineRatingDTOByWineId(@RequestParam Long wineId) {
        Optional<RatingGetDTO> optionalRatingDTO = ratingService.getWineRatingDTOByWineId(wineId);

        return optionalRatingDTO.map(ratingGetDTO -> new ResponseEntity<>(ratingGetDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/submitWineRating")
    @ResponseBody
    public ResponseEntity<Void> createWineRating(@RequestBody RatingPostDTO ratingPostDTO) {
        if(ratingService.createWineRating(ratingPostDTO)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteRating")
    public ResponseEntity<Void> deleteClickedRatingById(@RequestParam Long id) {
        if (ratingService.deleteClickedRatingById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/addWine")
    public ResponseEntity<Void> createWine(@RequestBody WinePostDTO winePostDTO) {
        if(wineService.createWine(winePostDTO, winePostDTO.getCustomerId())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
