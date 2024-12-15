package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.Rating;
import com.dziem.WineCellarManager.model.RatingGetDTO;
import com.dziem.WineCellarManager.model.RatingPostDTO;
import com.dziem.WineCellarManager.model.Wine;
import com.dziem.WineCellarManager.repository.RatingRepository;
import com.dziem.WineCellarManager.repository.WineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final WineRepository wineRepository;
    private final RatingRepository ratingRepository;
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
                                .id(rating.getId())
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

    @Override
    public boolean deleteClickedRatingById(Long id) {
        AtomicBoolean result = new AtomicBoolean(false);
        ratingRepository.findById(id).ifPresentOrElse(
                (existing) -> {
                    Wine wine = existing.getWine();
                    if (wine != null) {
                        wine.setRating(null);  // Remove the rating reference from the wine
                    }
                    ratingRepository.delete(existing);
                    result.set(true);
                }, () -> result.set(false));
        return result.get();
    }
}
