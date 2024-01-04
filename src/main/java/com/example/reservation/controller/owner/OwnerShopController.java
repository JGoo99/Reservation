package com.example.reservation.controller.owner;

import com.example.reservation.data.dto.owner.OwnerDetails;
import com.example.reservation.data.dto.reservation.ReservationDetailsDto;
import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.dto.shop.ShopDetailDto;
import com.example.reservation.service.impl.main.ReservationServiceImpl;
import com.example.reservation.service.impl.main.ShopServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private final Logger LOGGER = LoggerFactory.getLogger(OwnerShopController.class);

  @GetMapping("/add")
  public String addP() {

    return "owner/shop/add";
  }

  @PostMapping("/addProc")
  public String addProcP(@ModelAttribute ShopAddDto storeDto,
                         @AuthenticationPrincipal OwnerDetails details,
                         Model model) {

    if (details == null) {
      throw new RuntimeException("점장페이지의 로그인 정보가 존재하지 않습니다.");
    }

    ShopDetailDto shopDetails = shopService.add(storeDto, details.getEmail());
    model.addAttribute("shopDetails", shopDetails);

    return "owner/shop/add-success";
  }

  @GetMapping("/{shopId}")
  public String serviceSelectP(@PathVariable Long shopId,
                        Principal principal, Model model) {

    if (principal == null) {
      throw new RuntimeException("점장페이지의 로그인 정보가 존재하지 않습니다.");
    }
    model.addAttribute("shopId", shopId);

    return "owner/shop/service-select";
  }

  @GetMapping("/{shopId}/detail")
  public String detailP(@PathVariable Long shopId, Model model) {

    ShopDetailDto details = shopService.getOwnerShopDetails(shopId);
    model.addAttribute("details", details);

    return "owner/shop/detail";
  }

  @PostMapping("/deleteProc")
  public String deleteProcP(@RequestParam Long shopId) {

    boolean isDeleted = shopService.delete(shopId);

    return "owner/shop/delete-success";
  }

  @GetMapping("/edit")
  public String editP(@RequestParam Long shopId, Model model) {

    ShopDetailDto details = shopService.getOwnerShopDetails(shopId);
    model.addAttribute("details", details);

    return "owner/shop/edit";
  }

  @PostMapping("/editProc")
  public String editProcP(@ModelAttribute ShopDetailDto shopDetailDto, Model model) {
    LOGGER.info("[edit shopDetails]: {}", shopDetailDto.toString());

    ShopDetailDto details = shopService.edit(shopDetailDto);
    model.addAttribute("details", details);

    return "owner/shop/edit-success";
  }

  @GetMapping("/{shopId}/reserve")
  public String reserveP(@PageableDefault(page = 1) Pageable pageable,
                         @PathVariable Long shopId,
                         Model model) {

    Page<ReservationDetailsDto> acceptedList =
      reservationService.getAcceptedList(shopId, pageable);
    model.addAttribute("list", acceptedList);

    return "owner/shop/reserve";
  }

  @GetMapping("/{shopId}/review")
  public String reviewP(@PathVariable Long shopId,
                        Model model) {

    return "owner/shop/review";
  }
}
