package com.example.reservation.data.dto.reservation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccProcDto {
  private Long reservationId;
  private Integer isAccepted;
  private Long shopId;
}
