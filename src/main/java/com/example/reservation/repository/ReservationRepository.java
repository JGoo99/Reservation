package com.example.reservation.repository;

import com.example.reservation.data.dto.reservation.DateMapping;
import com.example.reservation.data.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  /**
   * 해당 매장의 예약 상태(대기, 승인, 거부)에 따라 페이징처리하여 반환
   */
  Page<Reservation> findAllByShopIdAndIsAccepted(
    Long shopId, int isAccepted, Pageable pageable);

  /**
   * 해당 날짜 확정 예약 조회 (해당 날짜의 0시0분 ~ 11시59분 조회)
   */
  List<DateMapping> findAllByReservedAtBetweenAndIsAcceptedAndShopId(
    LocalDateTime from, LocalDateTime to, int isAccepted, Long shopId);

  /**
   * 특정 유저의 예약이 있는지 확인
   * - 유저의 개인정보 변경 시 확인용으로 사용
   */
  boolean existsByUserId(Long userID);

  /**
   * 해당 유저의 현재시간 이후 예약 데이터 전부를 반환
   * - 유저의 개인정보 변경 시 현재시간 이후의 데이터만 변경
   */
  List<Reservation> findAllByReservedAtAfterAndUserId(LocalDateTime now, Long userId);
}
