package com.dziem.WineCellarManager.config;

import com.dziem.WineCellarManager.model.WineDTO;
import com.dziem.WineCellarManager.service.WineApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final WineApiService wineApiService;

    @Override
    public void run(String... args) throws Exception {
        List<WineDTO> wines = wineApiService.getWines();
        WineDTO wineDTO = wines.get(0);
        System.out.println(wineDTO);
        System.out.println(wines.size());
        wineApiService.loadWinesToDB();
    }
}
