package com.example.reservation.service.impl.main;

import com.example.reservation.data.dto.reservation.ReservationDetailsDto;
import com.example.reservation.data.entity.Reservation;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.service.inter.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;

  @Override
  public Page<ReservationDetailsDto> getAcceptedList(Long shopId, Pageable pageable) {
    PageRequest pageRequest =
      getPaging(pageable.getNumberOfPages() - 1, 5);

    Page<Reservation> selectedAcceptedList =
      reservationRepository.findAllByShopIdAndIsAccepted(shopId, 1, pageRequest);

    return selectedAcceptedList.map(ReservationDetailsDto::from);
  }

  @Override
  public PageRequest getPaging(int page, int size) {
    return PageRequest.of(page, size, Sort.by(DESC, "reservationTime"));
  }

}
