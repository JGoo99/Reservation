package com.example.reservation.data.dto.reservation;

import com.example.reservation.data.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class ReservationDetailsDto {

  private String userName;
  private String userPhone;
  private LocalDateTime time;
  public static ReservationDetailsDto from(Reservation reservation) {
    return ReservationDetailsDto.builder()
      .userName(reservation.getUserName())
      .userPhone(reservation.getUserPhone())
      .time(reservation.getReservationTime())
      .build();
  }
}
