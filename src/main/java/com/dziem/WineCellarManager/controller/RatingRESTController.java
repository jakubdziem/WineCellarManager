package com.dziem.WineCellarManager.controller;

import com.dziem.WineCellarManager.model.RatingGetDTO;
import com.dziem.WineCellarManager.model.RatingPostDTO;
import com.dziem.WineCellarManager.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@AllArgsConstructor
public class RatingRESTController {
    private final RatingService ratingService;
    @GetMapping("/getWineRating")
    @ResponseBody
    public ResponseEntity<RatingGetDTO> getWineRatingDTOByWineId(@RequestParam Long wineId) {
        Optional<RatingGetDTO> optionalRatingDTO = ratingService.getWineRatingDTOByWineId(wineId);

        return optionalRatingDTO.map(ratingGetDTO -> new ResponseEntity<>(ratingGetDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/submitWineRating")
    @ResponseBody
    public ResponseEntity<Void> createWineRating(@Validated @RequestBody RatingPostDTO ratingPostDTO) {
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
}
