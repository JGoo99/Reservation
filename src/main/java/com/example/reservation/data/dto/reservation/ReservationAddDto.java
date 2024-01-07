package com.example.reservation.data.dto.reservation;

import com.example.reservation.data.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@ToString
public class ReservationAddDto {

  private int year;
  private int month;

  private Integer date;
  private Long shopId;
  private String userName;
  private String userPhone;
  private Integer open;
  private Integer close;

  private List<Integer> times;

  public static Reservation toEntity(ReservationAddDto reservationAddDto) {
    List<Integer> times = reservationAddDto.getTimes();
    int from = times.get(0);
    int time = times.size();

    return Reservation.builder()
      .isVisited(false)
      .isAccepted(0)
      .reservedAt(LocalDateTime.of(
        reservationAddDto.getYear(),
        reservationAddDto.getMonth(),
        reservationAddDto.getDate(),
        from, 0))
      .time(time)
      .shopId(reservationAddDto.getShopId())
      .userName(reservationAddDto.userName)
      .userPhone(reservationAddDto.userPhone)
      .build();
  }
}
