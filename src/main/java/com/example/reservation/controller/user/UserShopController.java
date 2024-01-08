package com.example.reservation.controller.user;

import com.example.reservation.data.dto.reservation.ReservationAddDto;
import com.example.reservation.data.dto.reservation.ReservationInfoDto;
import com.example.reservation.data.dto.shop.ShopInfoDto;
import com.example.reservation.data.dto.user.CustomUserDetails;
import com.example.reservation.service.impl.main.ReservationServiceImpl;
import com.example.reservation.service.impl.main.ShopServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Queue;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/shop")
public class UserShopController {

  private final ShopServiceImpl shopService;
  private final ReservationServiceImpl reservationService;
  private final Logger LOGGER = LoggerFactory.getLogger(UserShopController.class);

  /**
   * 유저 메인화면에 보이는 매장 리스트의 상세정보
   * @param shopId .
   * @param model .
   * @return 매장 상세정보 페이지
   */
  @GetMapping("/info")
  public String infoP(@RequestParam Long shopId, Model model) {

    ShopInfoDto shopInfo = shopService.getShopInfo(shopId);
    model.addAttribute("shopInfo", shopInfo);

    return "user/shop/info";
  }

  /**
   * 매장 예약 : 년도와 달을 고르는 페이지
   * @return 매장 예약 중 년도와 달을 고르는 페이지
   */
  @GetMapping("/reserv")
  public String reservP(@RequestParam Long shopId, Model model) {

    model.addAttribute("shopId", shopId);

    return "user/shop/reserve";
  }

  /**
   * 매장 예약 : 날짜를 고르는 페이지
   */
  @PostMapping("/reserv.proc1")
  public String reservProc1P(@ModelAttribute ReservationAddDto addDto, Model model) {
    int lastDays =
      LocalDate.of(addDto.getYear(), addDto.getMonth() + 1, 1)
        .minusDays(1).getDayOfMonth();

    model.addAttribute("days", addDto);
    model.addAttribute("lastDays", lastDays);

    return "user/shop/reserve-day";
  }

  /**
   * 매장 예약 : 시각을 고르는 페이지
   * @return 예약가능한 시간만 선택할 수 있도록 한다.
   */
  @PostMapping("/reserv.proc2")
  public String reservProc2P(@ModelAttribute ReservationAddDto addDto, Model model) {

    Queue<Integer> availableTimes = reservationService.getAvailableTimeList(addDto);

    int lastDays =
      LocalDate.of(addDto.getYear(), addDto.getMonth() + 1, 1)
        .minusDays(1).getDayOfMonth();

    reservationService.setOpeningHours(addDto);

    model.addAttribute("days", addDto);
    model.addAttribute("availableTimes", availableTimes);
    model.addAttribute("lastDays", lastDays);

    return "user/shop/reserve-time";
  }

  /**
   * 매장 예약 : 모든 정보를 받아서 예약 db 에 저장.
   * @param addDto .
   * @param model .
   * @return 저장 시 예약정보를 확인할 수 있는 페이지
   */
  @PostMapping("/reserv.proc3")
  public String reservProc3P(@ModelAttribute ReservationAddDto addDto, Model model) {

    ReservationInfoDto infoDto = reservationService.save(addDto);
    model.addAttribute("info", infoDto);

    return "user/shop/reserve-success";
  }

  @PostMapping("/reserv/deleteProc")
  public String reservDeleteProc(@AuthenticationPrincipal CustomUserDetails details,
                                 @RequestParam Long reservationId) {

    boolean isDeleted = reservationService.deleteByUser(reservationId, details.getId());

    return "redirect:/user/reserv/info";
  }

  @PostMapping("/reserv/visitProc")
  public String reservVisitProc(@AuthenticationPrincipal CustomUserDetails details,
                                 @RequestParam Long reservationId) {

    boolean isDeleted = reservationService.visitByUSer(reservationId, details.getId());


    return "redirect:/user/reserv/info";
  }
}
