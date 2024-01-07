package com.example.reservation.repository;

import com.example.reservation.data.dto.reservation.DateMapping;
import com.example.reservation.data.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  Page<Reservation> findAllByShopIdAndIsAccepted(
    Long shopId, int isAccepted, Pageable pageable);

  // 해당 날짜 확정 예약 조회
  List<DateMapping> findAllByReservedAtBetweenAndIsAcceptedAndShopId(
    LocalDateTime from, LocalDateTime to, int isAccepted, Long shopId);

  Optional<Reservation> findByUserId(Long userId);
}
