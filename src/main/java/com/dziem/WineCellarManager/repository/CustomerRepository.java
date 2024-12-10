package com.dziem.WineCellarManager.repository;

import com.dziem.WineCellarManager.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
