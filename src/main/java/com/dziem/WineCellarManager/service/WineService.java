package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.Wine;

public interface WineService {
    Wine getClickedWineById(Long id);
    boolean editClickedWineById(Wine wine);
}
