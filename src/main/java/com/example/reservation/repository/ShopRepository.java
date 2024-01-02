package com.example.reservation.repository;

import com.example.reservation.data.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
  Optional<Shop> findByShopName(String shopName);

  boolean existsByShopName(String shopName);
}
