package com.example.reservation.repository;

import com.example.reservation.data.dto.reservation.DateMapping;
import com.example.reservation.data.dto.reservation.ReservationAddDto;
import com.example.reservation.data.dto.reservation.ReservationInfoDto;
import com.example.reservation.data.entity.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.Sort.Direction.DESC;

@SpringBootTest
@Transactional
class ReservationRepositoryTest {

  @Autowired
  ReservationRepository reservationRepository;

  @Test
  void save() {
    // given
    ReservationAddDto reservationAddDto = getReservationAddDto_1();

    // when
    Reservation savedReservation =
      reservationRepository.save(ReservationAddDto.toEntity(reservationAddDto));

    // then
    assertEquals(reservationAddDto.getYear(), savedReservation.getReservedAt().getYear());
    assertEquals(reservationAddDto.getMonth(), savedReservation.getReservedAt().getMonthValue());
    assertEquals(reservationAddDto.getDay(), savedReservation.getReservedAt().getDayOfMonth());
    assertEquals(reservationAddDto.getTimes().get(0), savedReservation.getReservedAt().getHour());
    assertEquals(reservationAddDto.getTimes().size(), savedReservation.getTime());
  }

  @Test
  void findAllByShopIdAndIsAccepted() {
    // given
    saveReservations();

    // when
    Page<Reservation> savedAcceptedList =
      reservationRepository.findAllByShopIdAndIsAccepted(1L, 1,
        PageRequest.of(0, 10, Sort.by(DESC, "reservedAt")));

    Page<ReservationInfoDto> acceptedList = savedAcceptedList.map(ReservationInfoDto::from);

    // then
    System.out.println(acceptedList.getContent().get(0).toString());
  }

  @Test
  @DisplayName("예약확정 리스트 조회")
  void findAllByReservedAtBetweenAndIsAcceptedAndShopId() {
    // given
    //13-15, 17-18 예약 가정
    saveReservations();

    ReservationAddDto request = getReservationAddDto_1();

    // then
    LocalDateTime date =
      LocalDateTime.of(request.getYear(), request.getMonth(), request.getDay(), 0, 0);

    List<DateMapping> acceptedList =
      reservationRepository.findAllByReservedAtBetweenAndIsAcceptedAndShopId(
        date, date.plusDays(1).minusMinutes(1), 1, 1L);

    List<Integer> available = new ArrayList<>();
    // 12시 - 19시 영업 가정
    for (int i = 12; i < 20; i++) {
      available.add(i);
    }

    for (int i = 0; i < acceptedList.size(); i++) {
      int idx = available.indexOf(acceptedList.get(i).getReservedAt().getHour());

      for (int j = 0; j < acceptedList.get(i).getTime(); j++) {
        available.remove(idx);
      }
    }

    System.out.println("예약 가능한 시간 출력");
    for (int i = 0; i < available.size(); i++) {
      System.out.print(available.get(i) + " ");
    }
  }

  ReservationAddDto getReservationAddDto_1() {
    List<Integer> times = new ArrayList();
    times.add(13);
    times.add(14);
    times.add(15);

    return ReservationAddDto.builder()
      .year(2024)
      .month(1)
      .day(3)
      .shopId(1L)
      .userName("구진")
      .userPhone("010-1234-5678")
      .times(times)
      .build();
  }

  ReservationAddDto getReservationAddDto_2() {
    List<Integer> times = new ArrayList();
    times.add(17);

    return ReservationAddDto.builder()
      .year(2024)
      .month(1)
      .day(3)
      .shopId(1L)
      .userName("구구")
      .userPhone("010-1234-5679")
      .times(times)
      .build();
  }

  void saveReservations() {
    ReservationAddDto reserve1 = getReservationAddDto_1();
    ReservationAddDto reserve2 = getReservationAddDto_2();

    // when
    Reservation savedReservation1 =
      reservationRepository.save(ReservationAddDto.toEntity(reserve1));
    Reservation savedReservation2 =
      reservationRepository.save(ReservationAddDto.toEntity(reserve2));

    // 예약 승인 후 다시 저장
    savedReservation1.setIsAccepted(1);
    savedReservation2.setIsAccepted(1);
    reservationRepository.save(savedReservation1);
    reservationRepository.save(savedReservation2);
  }
}