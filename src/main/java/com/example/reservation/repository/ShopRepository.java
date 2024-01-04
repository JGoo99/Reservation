package com.example.reservation.repository;

import com.example.reservation.data.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {

  boolean existsByShopName(String shopName);

  List<Shop> findAllByOwnerId(Long ownerId);

  Page<Shop> findAllByShopNameContainingIgnoreCase(String keyword, Pageable pageable);
}
