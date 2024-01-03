package com.example.reservation.controller.owner;

import com.example.reservation.data.dto.owner.OwnerDetails;
import com.example.reservation.data.dto.reservation.ReservationDetailsDto;
import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.dto.shop.ShopDetailDto;
import com.example.reservation.service.impl.main.ReservationServiceImpl;
import com.example.reservation.service.impl.main.ShopServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/owner/shop")
public class OwnerShopController {

  private final ShopServiceImpl shopService;
  private final ReservationServiceImpl reservationService;


  @GetMapping("/add")
  public String addP(Principal principal, Model model) {

    if (principal == null) {
      throw new RuntimeException("점장페이지의 로그인 정보가 존재하지 않습니다.");
    }
    String ownerName = principal.getName();
    model.addAttribute("name", ownerName);

    return "owner/shop/add";
  }

  @PostMapping("/addProc")
  public String addProcP(@AuthenticationPrincipal OwnerDetails details,
                         @ModelAttribute ShopAddDto storeDto,
                         Model model) {

    if (details == null) {
      throw new RuntimeException("점장페이지의 로그인 정보가 존재하지 않습니다.");
    }

    String email = details.getEmail();
    ShopDetailDto shopDetails = shopService.add(storeDto, email);

    model.addAttribute("name", details.getUsername());
    model.addAttribute("shopDetails", shopDetails);

    return "owner/shop/addSuccess";
  }

  @GetMapping("/{shopId}")
  public String detailP(Principal principal, Model model,
                        @PathVariable Long shopId) {
    if (principal == null) {
      throw new RuntimeException("점장페이지의 로그인 정보가 존재하지 않습니다.");
    }

    model.addAttribute("name", principal.getName());
    model.addAttribute("shopId", shopId);

    return "owner/shop/service-select";
  }

  @GetMapping("/{shopId}/reserve")
  public String reserveP(Principal principal, Model model,
                         @PageableDefault(page = 1) Pageable pageable,
                         @PathVariable Long shopId) {
    if (principal == null) {
      throw new RuntimeException("점장페이지의 로그인 정보가 존재하지 않습니다.");
    }

    Page<ReservationDetailsDto> acceptedList =
      reservationService.getAcceptedList(shopId, pageable);

    model.addAttribute("name", principal.getName());
    model.addAttribute("list", acceptedList);

    return "owner/shop/reserve";
  }

  @GetMapping("/{shopId}/review")
  public String reviewP(Principal principal, Model model,
                         @PathVariable Long shopId) {
    if (principal == null) {
      throw new RuntimeException("점장페이지의 로그인 정보가 존재하지 않습니다.");
    }

    model.addAttribute("name", principal.getName());

    return "owner/shop/review";
  }
}
