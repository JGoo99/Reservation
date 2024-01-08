package com.example.reservation.service.inter;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.reservation.AccProcDto;
import com.example.reservation.data.dto.reservation.ReservationAddDto;
import com.example.reservation.data.dto.reservation.ReservationInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;

import java.util.Queue;

public interface ReservationService {

   Page<ReservationInfoDto> getAcceptedList(Long shopId, Pageable pageable);

  PageRequest getPaging(int page, int size);

  Queue<Integer> getAvailableTimeList(ReservationAddDto addDto);

  ReservationInfoDto save(ReservationAddDto addDto);

  void setOpeningHours(ReservationAddDto addDto);

  Page<ReservationInfoDto> getUndefinedList(SearchDto searchDto, Long shopId);

  Boolean setIsAccepted(AccProcDto accProcDto);

  Page<ReservationInfoDto> getInfoByUser(SearchDto searchDto, Long userId);

  Boolean deleteByUser(Long reservationId);
}
