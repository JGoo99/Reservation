package com.example.reservation.repository;

import com.example.reservation.data.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  void deleteByShopId(Long shopId);

  boolean existsByShopId(Long shopId);

}
