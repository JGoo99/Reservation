package com.example.reservation.controller.user;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.shop.ShopInfoDto;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final ShopServiceImpl shopService;
  private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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

  @GetMapping("/my")
  public String myP() {

    return "user/my";
  }
}
