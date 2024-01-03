package com.example.reservation.repository;

import com.example.reservation.data.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  Page<Reservation> findAllByShopIdAndIsAccepted(Long shopId, int isAccepted,
                                                              Pageable pageable);

}
