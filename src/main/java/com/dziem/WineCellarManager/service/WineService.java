package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.RatingGetDTO;
import com.dziem.WineCellarManager.model.RatingPostDTO;
import com.dziem.WineCellarManager.model.WineDTO;

import java.util.Optional;

public interface WineService {
    WineDTO getClickedWineById(Long id);
    boolean updateClickedWineById(WineDTO wineDTO);

    boolean deleteClickedWineById(Long id);

    Optional<RatingGetDTO> getWineRatingDTOByWineId(Long wineId);

    boolean createWineRating(RatingPostDTO ratingPostDTO);
}
