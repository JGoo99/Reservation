package com.example.reservation.controller.owner;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.owner.OwnerDetails;
import com.example.reservation.data.dto.owner.OwnerJoinDto;
import com.example.reservation.data.dto.shop.ShopInfoDto;
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

  /**
   * 점장 메인페이지로 매장 리스트 및 로그인/회원가입 버튼 등이 있다.
   * @param page 현재 페이지
   * @param searchDto 페이징 및 검색 dto
   * @param pageable .
   * @param model .
   * @return 점장 메인페이지
   */
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

  /**
   * 개인정보 확인/수정 링크 및 점장이 등록한 매장 리스트를 보여준다.
   * @param details .
   * @param model .
   * @return 점장 마이페이지
   */
  @GetMapping("/my")
  public String myP(@AuthenticationPrincipal OwnerDetails details,
                    Model model) {

    List<ShopInfoDto> list = shopService.getOwnerShopList(details.getId());
    model.addAttribute("list", list);

    return "owner/my";
  }

  /**
   * 점장의 개인 상세정보를 보여준다.
   * @param details .
   * @param model .
   * @return 점장 개인정보 페이지
   */
  @GetMapping("/info")
  public String infoP(@AuthenticationPrincipal OwnerDetails details, Model model) {

    OwnerJoinDto ownerInfo = ownerMemberService.getOwnerInfo(details.getId());
    model.addAttribute("info", ownerInfo);

    return "owner/info";
  }

  /**
   * 비밀번호를 제외한 개인정보를 수정할 수 있다.
   * @param details .
   * @param model .
   * @return 점장 개인정보 수정 페이지
   */
  @GetMapping("/edit")
  public String infoEditP(@AuthenticationPrincipal OwnerDetails details,
                          Model model) {

    OwnerJoinDto ownerInfo = ownerMemberService.getOwnerInfo(details.getId());
    model.addAttribute("info", ownerInfo);

    return "owner/info-edit";
  }

  /**
   * 개인정보 수정 form 경로
   * @param editDto 비밀번호 제외 회원가입 dto 와 동일
   * @param details .
   * @param model .
   * @return 다시 개인상세정보 페이지로 반환하여 바뀐값을 보여준다.
   */
  @PostMapping("/editProc")
  public String infoEditProcP(@ModelAttribute OwnerJoinDto editDto,
                              @AuthenticationPrincipal OwnerDetails details,
                              Model model) {

    OwnerJoinDto ownerInfo = ownerMemberService.edit(editDto, details.getId());
    model.addAttribute("info", ownerInfo);

    return "owner/info";
  }

//  @GetMapping("/reserve/info")
//  public String reservInfoP(@RequestParam Long shopId) {
//
//
//    return "owner/reserv-info";
//  }
//
//  @GetMapping("/review/info")
//  public String reviewInfoP(@RequestParam Long shopId) {
//
//
//
//    return "owner/review-info";
//  }
}
