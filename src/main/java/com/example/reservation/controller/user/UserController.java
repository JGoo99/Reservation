package com.example.reservation.controller.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

  @GetMapping("/home")
  public String homeP(HttpSession session) {

    return "user/home";
  }
}
