package com.example.reservation.data.dto.reservation;

import com.example.reservation.data.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
public class ReservationAddDto {

  private int year;
  private int month;
  private int day;
  private int time;

  public static Reservation toEntity(ReservationAddDto reservationAddDto) {
    return Reservation.builder()
      .isVisited(false)
      .isAccepted(0)
      .reservationTime(LocalDateTime.of(
        reservationAddDto.getYear(),
        reservationAddDto.getMonth(),
        reservationAddDto.getDay(),
        reservationAddDto.getTime(), 0))
      .build();
  }

}
