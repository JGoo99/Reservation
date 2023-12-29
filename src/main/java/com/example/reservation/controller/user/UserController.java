package com.example.reservation.controller.user;

import com.example.reservation.data.dto.login.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

  @GetMapping("/home")
  public String homeP(Authentication authentication, HttpSession session) {
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    session.setAttribute("nickname", userDetails.getNickname());

    return "user/home";
  }
}
