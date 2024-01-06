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
public class ReservationInfoDto {

  private String shopName;
  private String address1;
  private String address2;

  private String userName;
  private String userPhone;

  private LocalDateTime reservedAt;
  private int time;

  private int isAccepted;
  private boolean isVisited;


  public static ReservationInfoDto from(Reservation reservation) {
    return ReservationInfoDto.builder()
      // shopName
      // address1
      // address2
      // isVisited
      .userName(reservation.getUserName())
      .userPhone(reservation.getUserPhone())
      .reservedAt(reservation.getReservedAt())
      .time(reservation.getTime())
      .isVisited(reservation.isVisited())
      .isAccepted(reservation.getIsAccepted())
      .build();
  }
}
