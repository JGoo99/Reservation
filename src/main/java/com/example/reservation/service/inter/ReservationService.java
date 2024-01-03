package com.example.reservation.service.inter;

import com.example.reservation.data.dto.reservation.ReservationDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;

public interface ReservationService {

  Page<ReservationDetailsDto> getAcceptedList(Long shopId, Pageable pageable);

  PageRequest getPaging(int page, int size);
}
