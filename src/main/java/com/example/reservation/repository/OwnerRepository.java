package com.example.reservation.repository;

import com.example.reservation.data.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

  Optional<Owner> findByEmail(String email);
  boolean existsByEmail(String email);
}
