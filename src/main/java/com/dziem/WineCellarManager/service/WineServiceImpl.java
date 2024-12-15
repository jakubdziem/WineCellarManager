package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.mapper.WineMapper;
import com.dziem.WineCellarManager.model.Rating;
import com.dziem.WineCellarManager.model.RatingDTO;
import com.dziem.WineCellarManager.model.Wine;
import com.dziem.WineCellarManager.model.WineDTO;
import com.dziem.WineCellarManager.repository.CustomerRepository;
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
    @Override
    public WineDTO getClickedWineById(Long id) {
        return wineMapper.wineToWineDTO(
                wineRepository.findById(id).orElse(new Wine()));
    }

    @Override
    public boolean editClickedWineById(WineDTO wine) {
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
    public Optional<RatingDTO> getWineRatingDTOByWineId(Long wineId) {
        AtomicReference<Optional<RatingDTO>> result = new AtomicReference<>(Optional.empty());
        wineRepository.findById(wineId).ifPresentOrElse(
                (existing) -> {
                    Optional<Rating> optionalRating = Optional.ofNullable(existing.getRating());
                    RatingDTO ratingDTO = new RatingDTO();
                    if(optionalRating.isPresent()) {
                        Rating rating = optionalRating.get();
                        ratingDTO = RatingDTO.builder()
                                .hasRating(true)
                                .ratingStars(rating.getRatingStars())
                                .flavour(rating.getFlavour())
                                .aroma(rating.getAroma())
                                .agingTime(rating.getAgingTime())
                                .suggestedFoodPairings(rating.getSuggestedFoodPairings())
                                .build();
                    } else {
                        ratingDTO.setHasRating(false);
                    }
                    result.set(Optional.of(ratingDTO));
                },
                () -> result.set(Optional.empty())
        );
        return result.get();
    }
}
