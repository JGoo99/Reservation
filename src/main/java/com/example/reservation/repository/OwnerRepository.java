package com.example.reservation.repository;

import com.example.reservation.data.entity.Owner;
import com.example.reservation.data.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

  Optional<Owner> findByEmail(String email);
  boolean existsByEmail(String email);
}
