package com.example.reservation.controller.user;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.service.impl.main.ShopServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final ShopServiceImpl shopService;

  @GetMapping("/home")
  public String homeP(@ModelAttribute SearchDto searchDto,
                      Model model) {

    return "user/home";
  }

  @GetMapping("/my")
  public String myP() {

    return "user/my";
  }
}
