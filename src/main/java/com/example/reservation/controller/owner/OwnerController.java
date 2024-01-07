package com.example.reservation.controller.owner;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.owner.OwnerDetails;
import com.example.reservation.data.dto.owner.OwnerJoinDto;
import com.example.reservation.data.dto.shop.ShopInfoDto;
import com.example.reservation.data.dto.user.UserJoinDto;
import com.example.reservation.service.impl.main.ShopServiceImpl;
import com.example.reservation.service.impl.member.OwnerMemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerController {

  private final ShopServiceImpl shopService;
  private final OwnerMemberServiceImpl ownerMemberService;
  private final Logger LOGGER = LoggerFactory.getLogger(OwnerController.class);

  @GetMapping("/home")
  public String homeP(@RequestParam(defaultValue = "1") int page,
                      @ModelAttribute SearchDto searchDto,
                      @PageableDefault(page = 1) Pageable pageable, Model model) {
    LOGGER.info("[owner searchDto]: {}", searchDto.toString());

    searchDto.setPageNum(page);
    Page<ShopInfoDto> list = shopService.getSearchedShopList(searchDto);
    searchDto.setPaging(pageable, list.getTotalPages());

    model.addAttribute("list", list);
    model.addAttribute("startPage", searchDto.getStartPage());
    model.addAttribute("endPage", searchDto.getEndPage());

    return "owner/home";
  }

  @GetMapping("/my")
  public String myP(@AuthenticationPrincipal OwnerDetails details,
                    Model model) {

    List<ShopInfoDto> list = shopService.getOwnerShopList(details.getId());
    model.addAttribute("list", list);

    return "owner/my";
  }

  @GetMapping("/info")
  public String infoP(@AuthenticationPrincipal OwnerDetails details, Model model) {

    OwnerJoinDto ownerInfo = ownerMemberService.getOwnerInfo(details.getId());
    model.addAttribute("info", ownerInfo);

    return "owner/info";
  }

  @GetMapping("/edit")
  public String infoEditP(@AuthenticationPrincipal OwnerDetails details,
                          Model model) {

    OwnerJoinDto ownerInfo = ownerMemberService.getOwnerInfo(details.getId());
    model.addAttribute("info", ownerInfo);

    return "owner/info-edit";
  }

  @PostMapping("/editProc")
  public String infoEditProcP(@ModelAttribute OwnerJoinDto editDto,
                              @AuthenticationPrincipal OwnerDetails details,
                              Model model) {

    OwnerJoinDto ownerInfo = ownerMemberService.edit(editDto, details.getId());
    model.addAttribute("info", ownerInfo);

    return "owner/info";
  }

  @GetMapping("/reserve/info")
  public String reservInfoP(@RequestParam Long shopId) {


    return "owner/reserv-info";
  }

  @GetMapping("/review/info")
  public String reviewInfoP(@RequestParam Long shopId) {



    return "owner/review-info";
  }
}
