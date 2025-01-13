package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.mapper.WineMapper;
import com.dziem.WineCellarManager.model.*;
import com.dziem.WineCellarManager.repository.CustomerRepository;
import com.dziem.WineCellarManager.repository.WineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WineServiceImpl implements WineService{
    private final WineRepository wineRepository;
    private final WineMapper wineMapper;
    private final CustomerRepository customerRepository;
    @Override
    public WineDTO getClickedWineById(Long id) {
        return wineMapper.wineToWineDTO(
                wineRepository.findById(id).orElse(new Wine()));
    }

    @Override
    public boolean updateClickedWineById(WineDTO wine) {
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
                    existing.getCustomer().getWines().remove(existing);
                    customerRepository.save(existing.getCustomer());
                    result.set(true);
                }, () -> result.set(false));
        return result.get();
    }

    @Override
    public boolean createWine(WinePostDTO winePostDTO, UUID customerId) {
        AtomicBoolean result = new AtomicBoolean(false);
        customerRepository.findById(customerId).ifPresentOrElse(
                (existing) -> {
                    Wine wine = Wine.builder()
                            .customer(existing)
                            .name(winePostDTO.getName())
                            .vintage(winePostDTO.getVintage())
                            .imageUrl(winePostDTO.getImageUrl())
                            .country(winePostDTO.getCountry())
                            .region(winePostDTO.getRegion())
                            .winery(winePostDTO.getWinery())
                            .price(winePostDTO.getPrice())
                            .wineType(winePostDTO.getWineType())
                            .build();
                    existing.getWines().add(wine);
                    customerRepository.save(existing);
                    result.set(true);
                }, () -> result.set(false));
        return result.get();
    }

    @Override
    public Map<WineType, List<WineDTO>> groupWinesByType(String nickname) {
        Optional<Customer> opt = customerRepository.findByNickname(nickname);
        if(opt.isPresent()) {
            return opt.get().getWines().stream().sorted(Comparator.comparing(Wine::getId)).map(wineMapper::wineToWineDTO).collect(Collectors.groupingBy(WineDTO::getWineType));
        } else {
            return new HashMap<>();
        }
    }

    @Override
    public List<WineDTO> sortWinesByMode(String nickname, String mode) {
        Optional<Customer> opt = customerRepository.findByNickname(nickname);
        if(opt.isPresent()) {
            return switch (mode) {
                case "Winery" ->
                        opt.get().getWines().stream().sorted(Comparator.comparing(Wine::getWinery)).map(wineMapper::wineToWineDTO).toList();
                case "Price" ->
                        opt.get().getWines().stream().sorted(Comparator.comparing(Wine::getPrice)).map(wineMapper::wineToWineDTO).toList();
                case "Name" ->
                        opt.get().getWines().stream().sorted(Comparator.comparing(Wine::getName)).map(wineMapper::wineToWineDTO).toList();
                case "Country" ->
                        opt.get().getWines().stream().sorted(Comparator.comparing(Wine::getCountry)).map(wineMapper::wineToWineDTO).toList();
                case "Region" ->
                        opt.get().getWines().stream().sorted(Comparator.comparing(Wine::getRegion)).map(wineMapper::wineToWineDTO).toList();
                case "Vintage" ->
                        opt.get().getWines().stream().sorted(Comparator.comparing(Wine::getVintage)).map(wineMapper::wineToWineDTO).toList();
                default ->
                        opt.get().getWines().stream().sorted(Comparator.comparing(Wine::getVintage)).map(wineMapper::wineToWineDTO).toList();
            };
        } else {
            return new ArrayList<>();
        }
    }
}
