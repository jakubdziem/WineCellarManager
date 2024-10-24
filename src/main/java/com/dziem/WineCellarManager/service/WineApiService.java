package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.WineDTO;

import java.util.List;
public interface WineApiService {
    List<WineDTO> getWines();
    void loadWinesToDB();
}
