package com.dziem.WineCellarManager.repository;

import com.dziem.WineCellarManager.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
