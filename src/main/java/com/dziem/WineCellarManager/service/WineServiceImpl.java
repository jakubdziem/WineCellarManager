package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.Wine;
import com.dziem.WineCellarManager.repository.WineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class WineServiceImpl implements WineService{
    private final WineRepository wineRepository;
    @Override
    public Wine getClickedWineById(Long id) {
        return wineRepository.findById(id).orElse(new Wine());
    }

    @Override
    public boolean editClickedWineById(Wine wine) {
        AtomicBoolean result = new AtomicBoolean(false);
        wineRepository.findById(wine.getId()).ifPresentOrElse(
                existing -> {
                    existing.setName(wine.getName());
                    existing.setVintage(wine.getVintage());
                    existing.setImageUrl(wine.getImageUrl());
                    existing.setCountry(wine.getCountry());
                    existing.setRegion(wine.getRegion());
                    existing.setWinery(wine.getWinery());
                    existing.setPrice(wine.getPrice());
                    existing.setWineType(wine.getWineType());
                    wineRepository.save(existing);
                    result.set(true);
                },
        () -> result.set(false));
        return result.get();
    }

    @Override
    public boolean deleteClickedWineById(Long id) {
        AtomicBoolean result = new AtomicBoolean(false);
        wineRepository.findById(id).ifPresentOrElse(
                (existing) -> {
                    wineRepository.delete(existing);
                    result.set(true);
                }, () -> result.set(false));
        return result.get();
    }
}
