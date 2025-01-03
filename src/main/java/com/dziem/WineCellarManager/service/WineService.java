package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.WineDTO;
import com.dziem.WineCellarManager.model.WinePostDTO;

import java.util.UUID;


public interface WineService {
    WineDTO getClickedWineById(Long id);
    boolean updateClickedWineById(WineDTO wineDTO);

    boolean deleteClickedWineById(Long id);

    boolean createWine(WinePostDTO winePostDTO, UUID customerId);
}
