package com.example.reservation.controller.owner;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.reservation.AccProcDto;
import com.example.reservation.data.dto.reservation.ReservationInfoDto;
import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.dto.shop.ShopInfoDto;
import com.example.reservation.service.impl.main.ReservationServiceImpl;
import com.example.reservation.service.impl.main.ShopServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/owner/shop")
public class OwnerShopController {

  private final ShopServiceImpl shopService;
  private final ReservationServiceImpl reservationService;
  private final Logger LOGGER = LoggerFactory.getLogger(OwnerShopController.class);

  /**
   * @return 매장 등록 페이지
   */
  @GetMapping("/add")
  public String addP() {

    return "owner/shop/add";
  }

  /**
   * 매장 등록 form 경로
   * @param shopAddDto 매장 등록 dto
   * @param model .
   * @return 저장된 데이터를 보여주는 성공 화면
   */
  @PostMapping("/addProc")
  public String addProcP(@ModelAttribute ShopAddDto shopAddDto, Model model) {
    LOGGER.info("[add shop]: {}", shopAddDto);

    ShopInfoDto shopInfo = shopService.add(shopAddDto);
    model.addAttribute("shopInfo", shopInfo);

    return "owner/shop/add-success";
  }

  /**
   * 점장 마이페이지에서 매장을 클릭할 경우 나오는 페이지로,
   * 매장정보 확인 및 삭제, 예약 관리, 리뷰 관리 서비스를 제공한다.
   * @param shopId .
   * @param principal .
   * @param model .
   * @return 매장관련 3가지 서비스를 선택할 수 있는 페이지
   */
  @GetMapping("/{shopId}")
  public String serviceSelectP(@PathVariable Long shopId,
                        Principal principal, Model model) {

    if (principal == null) {
      throw new RuntimeException("점장페이지의 로그인 정보가 존재하지 않습니다.");
    }
    model.addAttribute("shopId", shopId);

    return "owner/shop/service-select";
  }

  /**
   * 매장 상세정보를 보여준다.
   * @param shopId .
   * @param model .
   * @return 매장 상세정보 페이지
   */
  @GetMapping("/info")
  public String infoP(@RequestParam Long shopId, Model model) {

    ShopInfoDto shopInfo = shopService.getShopInfo(shopId);
    model.addAttribute("shopInfo", shopInfo);

    return "owner/shop/info";
  }

  /**
   * 매장 삭제 페이지
   * @param shopId .
   * @param model .
   * @return 매장 삭제 페이지
   */
  @PostMapping("/deleteProc")
  public String deleteProcP(@RequestParam Long shopId, Model model) {

    boolean isDeleted = shopService.delete(shopId);
    model.addAttribute("shopId", shopId);

    return "owner/shop/delete-success";
  }

  /**
   * 매장 정보 수정할 수 있는 페이지
   * @param shopId .
   * @param model .
   * @return 매장 정보 수정 페이지
   */
  @GetMapping("/edit")
  public String editP(@RequestParam Long shopId, Model model) {

    ShopInfoDto shopInfo = shopService.getShopInfo(shopId);
    model.addAttribute("shopInfo", shopInfo);

    return "owner/shop/edit";
  }

  /**
   * 매장 정보 수정 form 경로
   * @param shopInfoDto 기존값 혹은 변경값을 담은 dto
   * @param model .
   * @return 수정 시 변경된 값을 보여주는 성공 화면
   */
  @PostMapping("/editProc")
  public String editProcP(@ModelAttribute ShopInfoDto shopInfoDto, Model model) {
    LOGGER.info("[edit shopInfo]: {}", shopInfoDto.toString());

    ShopInfoDto shopInfo = shopService.edit(shopInfoDto);
    model.addAttribute("shopInfo", shopInfo);

    return "owner/shop/edit-success";
  }

  /**
   * 매장의 예약 정보를 보여준다.
   */
  @GetMapping("/reserv/info")
  public String reservInfoP(@RequestParam Long shopId,
                            @RequestParam(defaultValue = "1") int page,
                            @ModelAttribute SearchDto searchDto,
                            @PageableDefault(page = 1) Pageable pageable, Model model) {

    searchDto.setPageNum(page);
    Page<ReservationInfoDto> list = reservationService.getUndefinedList(searchDto, shopId);
    searchDto.setPaging(pageable, list.getTotalPages());

    model.addAttribute("shopId", shopId);
    model.addAttribute("list", list);
    model.addAttribute("startPage", searchDto.getStartPage());
    model.addAttribute("endPage", searchDto.getEndPage());

    return "owner/shop/reserv-info";
  }

  @PostMapping("/reserv/accProc")
  public String accProc(@ModelAttribute AccProcDto accProcDto) {
    LOGGER.info(accProcDto.toString());

    reservationService.setIsAccepted(accProcDto);

    return "redirect:/owner/shop/reserv/info?shopId=" + accProcDto.getShopId();
  }

  @GetMapping("/reserv/detail")
  public String reservDetailP(@RequestParam Long reservId, Model model) {

    return "owner/shop/reserv-detail";
  }

  /**
   * 매장의 리뷰를 보여준다.
   */
  @GetMapping("/review/info")
  public String reviewInfoP(@RequestParam Long shopId) {



    return "owner/review-info";
  }
}
