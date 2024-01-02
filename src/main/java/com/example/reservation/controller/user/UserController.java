package com.example.reservation.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  @GetMapping("/home")
  public String homeP(Principal principal, Model model) {

    if (principal != null) {
      String username = principal.getName();
      model.addAttribute("name", username);
    }
    return "user/home";
  }

  @GetMapping("/my")
  public String myP() {

    return "user/my";
  }
}
