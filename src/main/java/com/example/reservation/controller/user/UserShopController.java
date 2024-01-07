package com.example.reservation.controller.user;

import com.example.reservation.data.dto.reservation.ReservationAddDto;
import com.example.reservation.data.dto.reservation.ReservationInfoDto;
import com.example.reservation.data.dto.shop.ShopInfoDto;
import com.example.reservation.service.impl.main.ReservationServiceImpl;
import com.example.reservation.service.impl.main.ShopServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  @GetMapping("/info")
  public String infoP(@RequestParam Long shopId, Model model) {

    ShopInfoDto shopInfo = shopService.getShopInfo(shopId);
    model.addAttribute("shopInfo", shopInfo);

    return "user/shop/info";
  }

  @GetMapping("/reserv")
  public String reservP(@RequestParam Long shopId, Model model) {

    model.addAttribute("shopId", shopId);

    return "user/shop/reserve";
  }

  @PostMapping("/reserv.proc1")
  public String reservProc1P(@ModelAttribute ReservationAddDto addDto, Model model) {
    int lastDays =
      LocalDate.of(addDto.getYear(), addDto.getMonth() + 1, 1)
        .minusDays(1).getDayOfMonth();

    model.addAttribute("days", addDto);
    model.addAttribute("lastDays", lastDays);

    return "user/shop/reserve-day";
  }

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

  @PostMapping("/reserv.proc3")
  public String reservProc3P(@ModelAttribute ReservationAddDto addDto, Model model) {
    LOGGER.info("[reserve]: {}", addDto.toString());

    ReservationInfoDto infoDto = reservationService.save(addDto);
    model.addAttribute("info", infoDto);

    return "user/shop/reserve-success";
  }
}
