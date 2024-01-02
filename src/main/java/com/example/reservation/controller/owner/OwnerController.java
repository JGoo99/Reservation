package com.example.reservation.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerController {

  @GetMapping("/home")
  public String homeP(Principal principal, Model model) {

    if (principal != null) {
      String ownerName = principal.getName();
      model.addAttribute("name", ownerName);
    }
    return "owner/home";
  }

  @GetMapping("/my")
  public String myP(Principal principal, Model model) {

    if (principal != null) {
      String ownerName = principal.getName();
      model.addAttribute("name", ownerName);
    }
    return "owner/my";
  }
}
