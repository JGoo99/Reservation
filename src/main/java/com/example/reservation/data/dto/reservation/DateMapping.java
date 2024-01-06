package com.example.reservation.data.dto.reservation;

import java.time.LocalDateTime;

public interface DateMapping {
  LocalDateTime getReservedAt();

  int getTime();
}
