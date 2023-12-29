package com.example.reservation.controller.user;

import com.example.reservation.data.dto.join.UserJoinDto;
import com.example.reservation.data.dto.login.UserLoginDto;
import com.example.reservation.service.impl.user.UserMemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserMemberController {

  private final Logger LOGGER = LoggerFactory.getLogger(UserMemberController.class);
  private final UserMemberServiceImpl userMemberService;

  @GetMapping("/join")
  public String joinP() {

    return "user/join";
  }

  @PostMapping("/joinProc")
  public String joinProcP(UserJoinDto userJoinDto) {
    LOGGER.info(userJoinDto.toString());

    userMemberService.join(userJoinDto);

    return "redirect:user/login";
  }

  @GetMapping("/login")
  public String loginP() {

    return "user/login";
  }

  @PostMapping("/loginProc")
  public String loginProcP(UserLoginDto userLoginDto) {
    LOGGER.info(userLoginDto.toString());

    userMemberService.login(userLoginDto);

    return "redirect:user/login";
  }
}

