package com.dziem.WineCellarManager.repository;

import com.dziem.WineCellarManager.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
public interface WineRepository extends JpaRepository<Wine, Long> {
}
