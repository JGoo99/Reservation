package com.example.reservation.service.impl.main;

import com.example.reservation.data.dto.reservation.DateMapping;
import com.example.reservation.data.dto.reservation.ReservationAddDto;
import com.example.reservation.data.dto.reservation.ReservationInfoDto;
import com.example.reservation.data.entity.Reservation;
import com.example.reservation.data.entity.Shop;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.ShopRepository;
import com.example.reservation.service.inter.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;
  private final ShopRepository shopRepository;

  @Override
  public Page<ReservationInfoDto> getAvailableTimeList(Long shopId, Pageable pageable) {
    PageRequest pageRequest =
      getPaging(pageable.getNumberOfPages() - 1, 5);

    Page<Reservation> selectedAcceptedList =
      reservationRepository.findAllByShopIdAndIsAccepted(shopId, 1, pageRequest);

    return selectedAcceptedList.map(ReservationInfoDto::from);
  }

  @Override
  public PageRequest getPaging(int page, int size) {
    return PageRequest.of(page, size, Sort.by(DESC, "reservedAt"));
  }

  @Override
  public List<Integer> getAvailableTimeList(ReservationAddDto addDto) {
    List<Integer> available = new ArrayList<>();
    Shop shop = shopRepository.findById(addDto.getShopId())
      .orElseThrow(() -> new RuntimeException("존재하지 않는 매장입니다."));

    LocalDateTime request =
      LocalDateTime.of(addDto.getYear(), addDto.getMonth(), addDto.getDay(), 0, 0);

    List<DateMapping> acceptedList =
      reservationRepository.findAllByReservedAtBetweenAndIsAcceptedAndShopId(
        request, request.plusDays(1).minusMinutes(1), 1, addDto.getShopId());

    for (int i = shop.getOpen(); i <= shop.getClose(); i++) {
      available.add(i);
    }
    // 14 2 / 16 1
    for (int i = 0; i < acceptedList.size(); i++) {
      int idx = available.indexOf(acceptedList.get(i).getReservedAt().getHour());

      for (int j = 0; j < acceptedList.get(i).getTime(); j++) {
        available.remove(idx++);
      }
    }
    return available;
  }

  @Override
  public boolean save(ReservationAddDto addDto) {
    reservationRepository.save(ReservationAddDto.toEntity(addDto));
    return true;
  }
}
