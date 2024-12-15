package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.RatingDTO;
import com.dziem.WineCellarManager.model.WineDTO;

import java.util.Optional;

public interface WineService {
    WineDTO getClickedWineById(Long id);
    boolean editClickedWineById(WineDTO wineDTO);

    boolean deleteClickedWineById(Long id);

    Optional<RatingDTO> getWineRatingDTOByWineId(Long wineId);
}
