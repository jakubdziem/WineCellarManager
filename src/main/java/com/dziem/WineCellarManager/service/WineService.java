package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.WineDTO;

public interface WineService {
    WineDTO getClickedWineById(Long id);
    boolean editClickedWineById(WineDTO wineDTO);

    boolean deleteClickedWineById(Long id);
}
