package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.WineFromApiDTO;

import java.util.List;
public interface WineApiService {
    List<WineFromApiDTO> getWines();
    void loadWinesToDB();
}
