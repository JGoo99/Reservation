package com.example.reservation.service.impl.main;

import com.example.reservation.data.dto.reservation.DateMapping;
import com.example.reservation.data.dto.reservation.ReservationAddDto;
import com.example.reservation.data.dto.reservation.ReservationInfoDto;
import com.example.reservation.data.dto.shop.ShopInfoDto;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

    Shop shop = shopRepository.findById(shopId)
      .orElseThrow(() -> new RuntimeException("존재하지 않는 매장입니다."));

    return selectedAcceptedList.map(m -> {
      return ReservationInfoDto.from(m, ShopInfoDto.fromEntity(shop));
    });
  }

  @Override
  public PageRequest getPaging(int page, int size) {
    return PageRequest.of(page, size, Sort.by(DESC, "reservedAt"));
  }

  @Override
  public Queue<Integer> getAvailableTimeList(ReservationAddDto addDto) {
    List<Integer> available = new ArrayList<>();
    Shop shop = shopRepository.findById(addDto.getShopId())
      .orElseThrow(() -> new RuntimeException("존재하지 않는 매장입니다."));

    LocalDateTime request =
      LocalDateTime.of(addDto.getYear(), addDto.getMonth(), addDto.getDate(), 0, 0);

    List<DateMapping> acceptedList =
      reservationRepository.findAllByReservedAtBetweenAndIsAcceptedAndShopId(
        request, request.plusDays(1).minusMinutes(1), 1, addDto.getShopId());

    // 영업시간에서 예약확정시간 제외
    for (int i = shop.getOpen(); i <= shop.getClose(); i++) {
      available.add(i);
    }

    for (int i = 0; i < acceptedList.size(); i++) {
      int idx = available.indexOf(acceptedList.get(i).getReservedAt().getHour());

      for (int j = 0; j < acceptedList.get(i).getTime(); j++) {
        available.remove(idx);
      }
    }

    // 현재 시간 기준 10분 미만으로 남은 예약은 선택못하도록 처리
    LocalDateTime now = LocalDateTime.now();
    if (now.getYear() == addDto.getYear() && now.getMonthValue() == addDto.getMonth()
            && now.getDayOfMonth() == addDto.getDate()) {

      // from 시 부터 예약가능
      int from = now.plusMinutes(10).getHour() + 1;

      for (int i = 0; i < from; i++) {
        available.remove((Integer) i);
      }
    }

    return new LinkedList<>(available);
  }

  @Override
  public ReservationInfoDto save(ReservationAddDto addDto) {
    Shop shop = shopRepository.findById(addDto.getShopId())
      .orElseThrow(() -> new RuntimeException("존재하지 않는 매장입니다."));

    Reservation reservation =
      reservationRepository.save(ReservationAddDto.toEntity(addDto));

    return ReservationInfoDto.from(reservation, ShopInfoDto.fromEntity(shop));
  }

  @Override
  public void setOpeningHours(ReservationAddDto addDto) {
    Shop shop = shopRepository.findById(addDto.getShopId())
      .orElseThrow(() -> new RuntimeException("존재하지 않는 매장입니다."));

    addDto.setOpen(shop.getOpen());
    addDto.setClose(shop.getClose());
  }
}
