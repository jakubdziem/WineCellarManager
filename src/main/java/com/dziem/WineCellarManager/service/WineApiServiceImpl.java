package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.Wine;
import com.dziem.WineCellarManager.model.WineDTO;
import com.dziem.WineCellarManager.model.WineType;
import com.dziem.WineCellarManager.repository.WineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WineApiServiceImpl implements WineApiService {
    private final WineRepository wineRepository;
    private final RestTemplate restTemplate;
    private final WineConverter wineConverter;

    private List<WineDTO> getWinesByType(String url, WineType wineType) {
        WineDTO[] wineArray = restTemplate.getForObject(url, WineDTO[].class);
        for(WineDTO wineDTO : wineArray) {
            wineDTO.setWineType(wineType);
        }
        return Arrays.asList(wineArray);
    }
    @Override
    public List<WineDTO> getWines() {
        List<WineDTO> allWines = new ArrayList<>();
        String url = "https://api.sampleapis.com/wines/";
        for(WineType wineType : WineType.values()) {
            String type = wineType.toString().toLowerCase();
            if(type.equals("red") || type.equals("white")) {
                type = type.concat("s");
            }
            allWines.addAll(getWinesByType(url.concat(type), wineType));
        }
        return allWines;
    }

    @Override
    public void loadWinesToDB() {
        List<WineDTO> winesDTO = getWines();
        List<Wine> wines = new ArrayList<>();
        for(WineDTO wineDTO : winesDTO) {
            wines.add(wineConverter.convertWineDTOToWine(wineDTO));
        }
        wineRepository.saveAll(wines);
    }
}

