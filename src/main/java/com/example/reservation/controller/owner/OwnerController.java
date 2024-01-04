package com.example.reservation.controller.owner;

import com.example.reservation.data.dto.owner.OwnerDetails;
import com.example.reservation.data.dto.shop.ShopDetailDto;
import com.example.reservation.service.impl.main.ShopServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerController {

  private final ShopServiceImpl shopService;

  @GetMapping("/home")
  public String homeP() {

    return "owner/home";
  }

  @GetMapping("/my")
  public String myP(@AuthenticationPrincipal OwnerDetails details,
                    Model model) {

    List<ShopDetailDto> shopDetails = shopService.getOwnerShopList(details.getId());
    model.addAttribute("list", shopDetails);

    return "owner/my";
  }
}
