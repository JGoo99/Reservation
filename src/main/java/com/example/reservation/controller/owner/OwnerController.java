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

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerController {

  private final ShopServiceImpl shopService;

  @GetMapping("/home")
  public String homeP(Principal principal, Model model) {

    if (principal != null) {
      model.addAttribute("name", principal.getName());
    }

    return "owner/home";
  }

  @GetMapping("/my")
  public String myP(@AuthenticationPrincipal OwnerDetails details,
                    Model model) {

    if (details == null) {
      throw new RuntimeException("점장페이지의 로그인 정보가 존재하지 않습니다.");
    }

    List<ShopDetailDto> shopDetails = shopService.getShopList(details.getId());

    model.addAttribute("name", details.getUsername());
    model.addAttribute("list", shopDetails);

    return "owner/my";
  }
}
