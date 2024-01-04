package com.example.reservation.controller.owner;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.owner.OwnerDetails;
import com.example.reservation.data.dto.shop.ShopDetailDto;
import com.example.reservation.service.impl.main.ShopServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerController {

  private final ShopServiceImpl shopService;
  private final Logger LOGGER = LoggerFactory.getLogger(OwnerController.class);

  @GetMapping("/home")
  public String homeP(@ModelAttribute SearchDto searchDto,
                      Model model) {
    LOGGER.info("[owner searchDto]: {}", searchDto.toString());

    Page<ShopDetailDto> list = shopService.getSearchedShopList(searchDto);
    model.addAttribute("list", list);

    return "owner/home";
  }

  @GetMapping("/my")
  public String myP(@AuthenticationPrincipal OwnerDetails details,
                    Model model) {

    List<ShopDetailDto> list = shopService.getOwnerShopList(details.getId());
    model.addAttribute("list", list);

    return "owner/my";
  }
}
