package com.dziem.WineCellarManager.repository;

import com.dziem.WineCellarManager.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("SELECT c FROM Customer c WHERE c.nickname = :nickname")
    Optional<Customer> findByNickname(String nickname);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Customer c WHERE c.nickname = :nickname")
    Boolean existsByNickname(String nickname);
}
