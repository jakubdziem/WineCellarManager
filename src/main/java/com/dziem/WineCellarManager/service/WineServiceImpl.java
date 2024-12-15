package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.mapper.WineMapper;
import com.dziem.WineCellarManager.model.*;
import com.dziem.WineCellarManager.repository.CustomerRepository;
import com.dziem.WineCellarManager.repository.RatingRepository;
import com.dziem.WineCellarManager.repository.WineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class WineServiceImpl implements WineService{
    private final WineRepository wineRepository;
    private final WineMapper wineMapper;
    private final CustomerRepository customerRepository;
    private final RatingRepository ratingRepository;
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
    public Optional<RatingGetDTO> getWineRatingDTOByWineId(Long wineId) {
        AtomicReference<Optional<RatingGetDTO>> result = new AtomicReference<>(Optional.empty());
        wineRepository.findById(wineId).ifPresentOrElse(
                (existing) -> {
                    Optional<Rating> optionalRating = Optional.ofNullable(existing.getRating());
                    RatingGetDTO ratingGetDTO = new RatingGetDTO();
                    if(optionalRating.isPresent()) {
                        Rating rating = optionalRating.get();
                        ratingGetDTO = RatingGetDTO.builder()
                                .hasRating(true)
                                .ratingStars(rating.getRatingStars())
                                .flavour(rating.getFlavour())
                                .aroma(rating.getAroma())
                                .agingTime(rating.getAgingTime())
                                .suggestedFoodPairings(rating.getSuggestedFoodPairings())
                                .build();
                    } else {
                        ratingGetDTO.setHasRating(false);
                    }
                    result.set(Optional.of(ratingGetDTO));
                },
                () -> result.set(Optional.empty())
        );
        return result.get();
    }

    @Override
    public boolean createWineRating(RatingPostDTO ratingPostDTO) {
        AtomicBoolean result = new AtomicBoolean(false);
        wineRepository.findById(ratingPostDTO.getWineId()).ifPresentOrElse(
                (existing) -> {
                    Rating rating = Rating
                            .builder()
                            .customer(existing.getCustomer())
                            .ratingStars(ratingPostDTO.getRatingStars())
                            .flavour(ratingPostDTO.getFlavour())
                            .aroma(ratingPostDTO.getAroma())
                            .agingTime(ratingPostDTO.getAgingTime())
                            .suggestedFoodPairings(ratingPostDTO.getSuggestedFoodPairings())
                            .wine(existing)
                            .build();
                    ratingRepository.save(rating);
                    existing.setRating(rating);
                    wineRepository.save(existing);
                    result.set(true);
                }, () -> result.set(false));
        return result.get();
    }
}
