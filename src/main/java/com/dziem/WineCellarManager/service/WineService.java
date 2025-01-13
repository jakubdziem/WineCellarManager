package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.WineDTO;
import com.dziem.WineCellarManager.model.WinePostDTO;
import com.dziem.WineCellarManager.model.WineType;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface WineService {
    WineDTO getClickedWineById(Long id);
    boolean updateClickedWineById(WineDTO wineDTO);

    boolean deleteClickedWineById(Long id);

    boolean createWine(WinePostDTO winePostDTO, UUID customerId);
    Map<WineType, List<WineDTO>> groupWinesByType(String nickname);
    List<WineDTO> sortWinesByMode(String nickname, String mode);

}
