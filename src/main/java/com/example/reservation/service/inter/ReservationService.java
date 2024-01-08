package com.example.reservation.service.inter;

import com.example.reservation.data.dto.reservation.ReservationAddDto;
import com.example.reservation.data.dto.reservation.ReservationInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.Queue;

public interface ReservationService {

   Page<ReservationInfoDto> getAcceptedList(Long shopId, Pageable pageable);

  PageRequest getPaging(int page, int size);

  Queue<Integer> getAvailableTimeList(ReservationAddDto addDto);

  ReservationInfoDto save(ReservationAddDto addDto);

  void setOpeningHours(ReservationAddDto addDto);

}
