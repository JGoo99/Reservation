package com.example.reservation.data.dto.reservation;

import com.example.reservation.data.entity.Reservation;
import com.example.reservation.data.entity.Shop;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.format.TextStyle;
import java.util.Locale;

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

  private Integer year;
  private Integer month;
  private Integer day;
  private String week;
  private Integer hour;
  private Integer time;

  private String isAccepted;
  private String isVisited;


  public static ReservationInfoDto from(Reservation reservation, Shop shop) {
    String isAccepted = "";
    switch (reservation.getIsAccepted()) {
      case 0:
        isAccepted = "신청 완료";
        break;
      case -1:
        isAccepted = "예약 거부";
        break;
      case 1:
        isAccepted = "예약 승인";
        break;
      default:
        isAccepted = "ERROR(예약 오류)";
    }

    String isVisited = "";
    if (reservation.isVisited()) {
      isVisited = "방문 완료";
    } else {
      isVisited = "방문 기록이 없음";
    }

    return ReservationInfoDto.builder()
      .shopName(shop.getShopName())
      .address1(shop.getAddress1())
      .address2(shop.getAddress2())
      .userName(reservation.getUserName())
      .userPhone(reservation.getUserPhone())
      .year(reservation.getReservedAt().getYear())
      .month(reservation.getReservedAt().getMonthValue())
      .day(reservation.getReservedAt().getDayOfMonth())
      .week(reservation.getReservedAt().getDayOfWeek()
        .getDisplayName(TextStyle.NARROW, Locale.KOREA))
      .hour(reservation.getReservedAt().getHour())
      .time(reservation.getTime())
      .isVisited(isVisited)
      .isAccepted(isAccepted)
      .build();
  }

}
