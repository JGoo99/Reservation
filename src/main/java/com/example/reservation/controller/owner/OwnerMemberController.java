package com.example.reservation.controller.owner;

import com.example.reservation.data.dto.owner.OwnerJoinDto;
import com.example.reservation.data.dto.owner.OwnerLoginDto;
import com.example.reservation.service.impl.owner.OwnerMemberServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerMemberController {

  private final Logger LOGGER = LoggerFactory.getLogger(OwnerMemberController.class);
  private final OwnerMemberServiceImpl ownerMemberService;

  @GetMapping("/join")
  public String joinP() {

    return "owner/join";
  }

  @PostMapping("/joinProc")
  public String joinProcP(@ModelAttribute OwnerJoinDto ownerJoinDto) {
    LOGGER.info("[owner join] : {}", ownerJoinDto.toString());

    ownerMemberService.join(ownerJoinDto);
    return "owner/login";
  }

  @GetMapping("/login")
  public String loginP() {

    return "owner/login";
  }

  @PostMapping("/loginProc")
  public String loginProcP(@ModelAttribute OwnerLoginDto ownerLoginDto) {
    LOGGER.info("[owner login] : {}", ownerLoginDto.toString());

    boolean isLogin = ownerMemberService.login(ownerLoginDto);

    if (!isLogin) {
      return "redirect:/owner/login";
    }
    return "redirect:/owner/home";
  }

  @GetMapping("/logout")
  public String logoutP(HttpSession session) {

    if (session.getId() != null) {
      session.invalidate();
    }
    return "redirect:/";
  }
}
