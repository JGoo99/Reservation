package com.example.reservation.service.inter;

import com.example.reservation.data.dto.reservation.ReservationAddDto;
import com.example.reservation.data.dto.reservation.ReservationInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface ReservationService {

  Page<ReservationInfoDto> getAvailableTimeList(Long shopId, Pageable pageable);

  PageRequest getPaging(int page, int size);

  List<Integer> getAvailableTimeList(ReservationAddDto addDto);

  boolean save(ReservationAddDto addDto);
}
