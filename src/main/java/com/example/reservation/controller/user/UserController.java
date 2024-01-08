package com.example.reservation.controller.user;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.shop.ShopInfoDto;
import com.example.reservation.data.dto.user.CustomUserDetails;
import com.example.reservation.data.dto.user.UserJoinDto;
import com.example.reservation.service.impl.main.ShopServiceImpl;
import com.example.reservation.service.impl.member.UserMemberServiceImpl;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final ShopServiceImpl shopService;
  private final UserMemberServiceImpl userMemberService;
  private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  /**
   * 유저 메인페이지로 매장 리스트 및 로그인/회원가입 버튼 등이 있다.
   * @param page 현재 페이지
   * @param searchDto 페이징 정보 및 검색 dto
   * @param pageable .
   * @param model .
   * @return 유저 메인페이지
   */
  @GetMapping("/home")
  public String homeP(@RequestParam(defaultValue = "1") int page,
                      @ModelAttribute SearchDto searchDto,
                      @PageableDefault(page = 1) Pageable pageable, Model model) {
    LOGGER.info("[user searchDto]: {}", searchDto.toString());

    searchDto.setPageNum(page);
    Page<ShopInfoDto> list = shopService.getSearchedShopList(searchDto);
    searchDto.setPaging(pageable, list.getTotalPages());

    model.addAttribute("list", list);
    model.addAttribute("startPage", searchDto.getStartPage());
    model.addAttribute("endPage", searchDto.getEndPage());

    return "user/home";
  }

  /**
   * 유저에게 제공하는 내정보관리, 예약확인 및 도착, 리뷰작성 서비스를 선택할 수 았다.
   * @param details .
   * @param model .
   * @return 서비스 선택 페이지
   */
  @GetMapping("/my")
  public String myP(@AuthenticationPrincipal CustomUserDetails details, Model model) {

    model.addAttribute("userId", details.getId());

    return "user/my";
  }

  /**
   * 유저 개인 상세 정보 페이지
   * @param details .
   * @param model .
   * @return .
   */
  @GetMapping("/info")
  public String infoP(@AuthenticationPrincipal CustomUserDetails details,
                      Model model) {

    UserJoinDto userInfo = userMemberService.getUserInfo(details.getId());
    model.addAttribute("info", userInfo);
    model.addAttribute("userId", details.getId());

    return "user/info";
  }

  /**
   * 유저 개인정보 수정 페이지
   * @param details .
   * @param model .
   * @return .
   */
  @GetMapping("/edit")
  public String infoEditP(@AuthenticationPrincipal CustomUserDetails details,
                          Model model) {

    UserJoinDto userInfo = userMemberService.getUserInfo(details.getId());
    model.addAttribute("info", userInfo);

    return "user/info-edit";
  }

  /**
   * 유저 개인정보 수정 form 경로
   * @param editDto 기존값 및 변경값을 담은 dto
   * @param details .
   * @param model .
   * @return 변경된 값을 확인할 수 있도록 다시 상세정보 페이지로 반환
   */
  @PostMapping("/editProc")
  public String infoEditProcP(@ModelAttribute UserJoinDto editDto,
                              @AuthenticationPrincipal CustomUserDetails details,
                              Model model) {

      UserJoinDto userInfo = userMemberService.edit(editDto, details.getId());
      model.addAttribute("info", userInfo);

    return "user/info";
  }


  @GetMapping("/reserv/info")
  public String reservInfoP(@AuthenticationPrincipal CustomUserDetails details,
                            Model model) {


    return "user/reserv-info";
  }

  @GetMapping("/review/info")
  public String reviewInfoP(@AuthenticationPrincipal CustomUserDetails details,
                            Model model) {


    return "user/review-info";
  }

}
