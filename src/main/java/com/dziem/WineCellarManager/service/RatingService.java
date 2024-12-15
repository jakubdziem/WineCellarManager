package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.RatingGetDTO;
import com.dziem.WineCellarManager.model.RatingPostDTO;

import java.util.Optional;

public interface RatingService {
    Optional<RatingGetDTO> getWineRatingDTOByWineId(Long wineId);
    boolean createWineRating(RatingPostDTO ratingPostDTO);

    boolean deleteClickedRatingById(Long id);
}
