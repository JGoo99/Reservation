package com.example.reservation.service.impl.main;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.reservation.AccProcDto;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;
  private final ShopRepository shopRepository;

  /**
   * 확정된 예약 리스트를 페이징 처리하여 반환
   */
  @Override
  public Page<ReservationInfoDto> getAcceptedList(Long shopId, Pageable pageable) {
    PageRequest pageRequest =
      getPaging(pageable.getPageNumber() - 1, 5);

    Page<Reservation> selectedAcceptedList =
      reservationRepository.findAllByShopIdAndIsAccepted(shopId, 1, pageRequest);

    Shop shop = shopRepository.findById(shopId)
      .orElseThrow(() -> new RuntimeException("존재하지 않는 매장입니다."));


    return selectedAcceptedList.map(m -> {
      return ReservationInfoDto.from(m, shop);
    });
  }

  /**
   * 예약순서를 기준으로 정렬한 페이징 구현체를 반환한다.
   * @param page 현재 페이지
   * @param size 페이지 당 보여주는 데이터 수
   * @return pageable 구현체
   */
  @Override
  public PageRequest getPaging(int page, int size) {
    return PageRequest.of(page, size, Sort.by(DESC, "reservedAt"));
  }

  /**
   * 요청된 시간에 예약 가능한 시간을 큐에 담아서 리턴한다.
   * @param addDto 유저의 예약 신청 시 사용
   * @return 예약 가능한 시간 큐 [프론트단에서 타임리프 이중 반복문 사용하기 위해 큐를 사용]
   */
  @Override
  public Queue<Integer> getAvailableTimeList(ReservationAddDto addDto) {
    List<Integer> available = new ArrayList<>();
    Shop shop = shopRepository.findById(addDto.getShopId())
      .orElseThrow(() -> new RuntimeException("존재하지 않는 매장입니다."));

    LocalDateTime request =
      LocalDateTime.of(addDto.getYear(), addDto.getMonth(), addDto.getDate(), 0, 0);

    // 요청된 날짜에서 확정된 예약의 시간은 제외한다.
    List<DateMapping> acceptedList =
      reservationRepository.findAllByReservedAtBetweenAndIsAcceptedAndShopId(
        request, request.plusDays(1).minusMinutes(1), 1, addDto.getShopId());

    // 총 영업시간에서 확정예약의 시간 제외처리
    for (int i = shop.getOpen(); i <= shop.getClose(); i++) {
      available.add(i);
    }
    for (int i = 0; i < acceptedList.size(); i++) {
      int idx = available.indexOf(acceptedList.get(i).getReservedAt().getHour());

      for (int j = 0; j < acceptedList.get(i).getTime(); j++) {
        available.remove(idx);
      }
    }

    // 현재 시간 기준 10분 미만으로 남은 예약은 선택 못하도록 처리
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

  /**
   * 예약 요청이 들어온 시점을 기준으로 예약 시간 유효성을 한 번 더 체크 후 예약 정보를 저장한다.
   * @param addDto 최종 예약 신청 dto
   * @return 저장된 예약 정보를 반환
   */
  @Override
  public ReservationInfoDto save(ReservationAddDto addDto) {
    Shop shop = shopRepository.findById(addDto.getShopId())
      .orElseThrow(() -> new RuntimeException("존재하지 않는 매장입니다."));

    // 예약 10분 전 마감 더블체크
    LocalDateTime now = LocalDateTime.now();
    if (now.getYear() == addDto.getYear() && now.getMonthValue() == addDto.getMonth()
      && now.getDayOfMonth() == addDto.getDate()) {

      int curHour = LocalDateTime.now().plusMinutes(10).getHour();

      List<Integer> times = addDto.getTimes();
      for (int i = 0; i < times.size(); i++) {
        if (times.get(i) <= curHour) {
          throw new RuntimeException("예약할 수 없는 날짜입니다.");
        }
      }
    }

    Reservation reservation =
      reservationRepository.save(ReservationAddDto.toEntity(addDto));

    return ReservationInfoDto.from(reservation, shop);
  }

  /**
   * 해당 매장의 오픈시간, 마감시간을 초기화한다.
   * @param addDto 예약 신청 시 받아온 dto
   */
  @Override
  public void setOpeningHours(ReservationAddDto addDto) {
    Shop shop = shopRepository.findById(addDto.getShopId())
      .orElseThrow(() -> new RuntimeException("존재하지 않는 매장입니다."));

    addDto.setOpen(shop.getOpen());
    addDto.setClose(shop.getClose());
  }

  /**
   * 대기중인 예약 리스트 날짜 가까운 순으로 전체 반환
   */
  @Override
  public Page<ReservationInfoDto> getUndefinedList(SearchDto searchDto, Long shopId) {
    searchDto.setDirectionColumn("reservedAt");
    searchDto.setDirection(0);
    Pageable pageable = getPaging(searchDto);

    Page<Reservation> reservations =
      reservationRepository.findAllByShopIdAndIsAccepted(shopId, 0, pageable);

    Shop shop = shopRepository.findById(shopId)
      .orElseThrow(() -> new RuntimeException("해당 매장 정보가 유효하지 않습니다."));

    return reservations.map(m -> ReservationInfoDto.from(m, shop));
  }

  @Override
  public Boolean setIsAccepted(AccProcDto accProcDto) {
    Reservation reservation = reservationRepository.findById(accProcDto.getReservationId())
      .orElseThrow(() -> new RuntimeException("해당 예약 정보가 존재하지 않습니다."));

    reservation.setIsAccepted(accProcDto.getIsAccepted());
    reservationRepository.save(reservation);

    return true;
  }

  public PageRequest getPaging(SearchDto searchDto) {
    if (searchDto.getDirection() == 0) {
      return PageRequest.of(searchDto.getPageNum() - 1, searchDto.getSize(),
        Sort.by(ASC, searchDto.getDirectionColumn()));
    }
    return PageRequest.of(searchDto.getPageNum() - 1, searchDto.getSize(),
      Sort.by(DESC, searchDto.getDirectionColumn()));
  }
}
